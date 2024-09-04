package example.micronaut.gorm.controller

import example.micronaut.gorm.model.TransactionModel
import example.micronaut.gorm.service.TransactionService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

import javax.inject.Inject

@Controller("/transaction")
class TransactionController {

    @Inject
    TransactionService transactionService
    @Post("/transfer")
    def moneyTransfer(@Body TransactionModel transactionModel)
    {
        return transactionService.transferMoney(transactionModel.senderMobileNumber,
        transactionModel.receiverMobileNumber,transactionModel.amount,transactionModel.upiPin)
    }
    @Get("/history/{phoneNumber}")
    def transactionHistory(@PathVariable Long phoneNumber) {
        return transactionService.getTransactionHistory(phoneNumber)
    }

}
