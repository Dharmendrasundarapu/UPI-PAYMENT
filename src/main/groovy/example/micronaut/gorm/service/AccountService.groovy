package example.micronaut.gorm.service

import example.micronaut.gorm.domain.AccountManagement
import example.micronaut.gorm.domain.UserManagement
import example.micronaut.gorm.model.AccountModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class AccountService {
    @Transactional
    def createAccountDetails(AccountModel accountModel) {
        AccountManagement accountManagement =accountModel.toAccount(accountModel)
        accountManagement.user=UserManagement.get(accountModel.userId )
        accountManagement.save()
        return accountManagement

    }

    @Transactional
    def getByUserId(Long userId) {
        UserManagement userManagement = UserManagement.findById(userId)
        if (userManagement == null) {
            return null
        }

        def accountDetails = AccountManagement.findAllByUser(userManagement)
        def userDetails = accountDetails.collect { userDetail ->
            AccountModel.fromAccountManagement(userDetail)
        }

        return userDetails
    }


}
