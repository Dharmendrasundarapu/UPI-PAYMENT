package example.micronaut.gorm.service

import example.micronaut.gorm.domain.AccountManagement
import example.micronaut.gorm.domain.UserManagement
import example.micronaut.gorm.model.AccountModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton
@Singleton
class AccountService {

    @Transactional
    AccountManagement findByUserAndUpiPin(UserManagement user, String upiPin) {
        return AccountManagement.findByUserAndUpiPin(user, upiPin)
    }

    @Transactional
    AccountManagement findPrimaryAccount(UserManagement user) {
        return AccountManagement.findByUserAndIsPrimary(user, true)
    }

    @Transactional
    List<AccountManagement> findByUser(UserManagement user) {
        return AccountManagement.findAllByUser(user)
    }

    @Transactional
    def createAccountDetails(AccountModel accountModel) {
        AccountManagement accountManagement = accountModel.toAccount(accountModel)
        UserManagement user = UserManagement.get(accountModel.userId)
        if (!user) {
            return  ("User not found")
        }
        accountManagement.user = user

        def primaryAccount = findPrimaryAccount(user)
        if (!primaryAccount) {
            accountManagement.isPrimary = true
        } else {
            accountManagement.isPrimary = accountModel.isPrimary ?: false
        }

        accountManagement.save(flush: true)

        if (accountManagement.isPrimary) {
            unsetOtherPrimaryAccounts(accountManagement)
        }

        return accountManagement
    }

    @Transactional
    def getByUserId(Long userId) {
        UserManagement userManagement = UserManagement.findById(userId)
        if (!userManagement) {
            throw new IllegalArgumentException("User Not Found")
        }

        def accountDetails = findByUser(userManagement)
        return accountDetails.collect { userDetail ->
            AccountModel.fromAccountManagement(userDetail)
        }
    }

    @Transactional
    def setPrimaryAccount(Long accountId, Long userId) {
        UserManagement user = UserManagement.get(userId)
        if (!user) {
            throw new IllegalArgumentException("User Not Found")
        }

        AccountManagement account = AccountManagement.get(accountId)
        if (!account || account.user.id != userId) {
            throw new IllegalArgumentException("Account Not Found or doesn't belong to the user")
        }

        unsetOtherPrimaryAccounts(account)
        account.isPrimary = true
        account.save(flush: true)

        return account
    }

    private void unsetOtherPrimaryAccounts(AccountManagement newPrimaryAccount) {
        UserManagement user = newPrimaryAccount.user
        findByUser(user).findAll { it.isPrimary }.each { account ->
            if (account.id != newPrimaryAccount.id) {
                account.isPrimary = false
                account.save(flush: true)
            }
        }
    }
}
