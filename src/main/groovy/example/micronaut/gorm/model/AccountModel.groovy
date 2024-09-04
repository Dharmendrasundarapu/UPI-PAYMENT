package example.micronaut.gorm.model

import example.micronaut.gorm.domain.AccountManagement
import example.micronaut.gorm.domain.UserManagement

class AccountModel {
    Long id
    UserManagement userManagement
    String bankName
    Long userId
    BigDecimal accountNumber
    int  pin
    BigDecimal bankBalance = 100000
    int upiPin
    Long transactionLimit = 100
    static  AccountModel fromAccountManagement(AccountManagement accountManagement)
    {
        if(accountManagement==null)
        {
            return  null
        }
        return  new AccountModel(
                bankBalance: accountManagement.bankBalance
        )
    }

    static  AccountManagement toAccount(AccountModel  accountModel)
    {
        if(accountModel==null)
        {
            return null
        }
        AccountManagement accountManagement=new  AccountManagement()
        accountManagement.bankName=accountModel.bankName
        accountManagement.accountNumber=accountModel.accountNumber
        accountManagement.pin=accountModel.pin
        accountManagement.bankBalance=accountModel.bankBalance
        accountManagement.upiPin=accountModel.upiPin
        accountManagement.transactionLimit=accountModel.transactionLimit

        return accountManagement
    }
}
