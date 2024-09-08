package example.micronaut.gorm.controller

import example.micronaut.gorm.model.TransactionModel
import example.micronaut.gorm.service.TransactionService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject


@Controller("/transaction")
class TransactionController {
    private static final Logger LOG= LoggerFactory.getLogger(TransactionController.class)

    @Inject
    TransactionService transactionService;

    @Post("/transfer")
    public String transferMoney(@Body TransactionModel transactionModel) {
        LOG.info("Received transaction: {}", transactionModel);

        boolean result = transactionService.transferMoney(
                transactionModel.getSenderMobileNumber(),
                transactionModel.getReceiverMobileNumber(),
                transactionModel.getAmount(),
                transactionModel.getUpiPin()
        );

        if (result) {
            return "Transfer successful";
        } else {
            return "Transfer failed";
        }
    }

    @Get("/history/{phoneNumber}")
    def transactionHistory(@PathVariable Long phoneNumber) {
        return transactionService.getTransactionHistory(phoneNumber)
    }

}
