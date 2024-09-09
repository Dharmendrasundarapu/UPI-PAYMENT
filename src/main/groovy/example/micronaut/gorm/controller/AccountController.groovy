package example.micronaut.gorm.controller

import example.micronaut.gorm.model.AccountModel
import example.micronaut.gorm.service.AccountService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue

import javax.inject.Inject


@Controller("/account")
class AccountController {
    @Inject
    AccountService accountService

    @Post("/create")
    def saveAccount(@Body AccountModel accountModel)
    {
        accountService.createAccountDetails(accountModel)
        return "Account created Successfully"
    }
    @Get("/userId/{userId}")
    def getDetails(@PathVariable Long userId)
    {
        return  accountService.getByUserId(userId)

    }
    @Get("/upiPin")
    def checkBalance(@QueryValue int upiPin)
    {
        return accountService.getBalance(upiPin)
    }
}
