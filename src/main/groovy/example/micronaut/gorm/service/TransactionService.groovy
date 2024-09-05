package example.micronaut.gorm.service

import example.micronaut.gorm.domain.AccountManagement
import example.micronaut.gorm.domain.TransactionManagement
import example.micronaut.gorm.domain.UserManagement
import example.micronaut.gorm.model.TransactionModel
import grails.gorm.transactions.Transactional

import javax.inject.Inject
import javax.inject.Singleton
import java.text.SimpleDateFormat

@Singleton
class TransactionService {

    // Inject the AccountService
    @Inject
    AccountService accountService

    @Transactional
    def transferMoney(String senderMobileNumber, String receiverMobileNumber, BigDecimal amount, String upiPin) {
        UserManagement sender = UserManagement.findByPhoneNumber(senderMobileNumber)
        UserManagement receiver = UserManagement.findByPhoneNumber(receiverMobileNumber)

        if (!sender || !receiver) {
            return "Sender or Receiver Not Found"
        }

        // Fetch sender's account using UPI Pin via AccountService
        AccountManagement senderAccount = accountService.findByUserAndUpiPin(sender, upiPin)
        if (!senderAccount) {
            return "Invalid UPI Pin"
        }

        // Fetch receiver's primary account, or fallback to the default account
        AccountManagement receiverAccount = accountService.findPrimaryAccount(receiver)
        if (!receiverAccount) {
            receiverAccount = accountService.findByUser(receiver)?.first()
        }

        if (!receiverAccount) {
            return "Receiver Account Not Found"
        }

        // Check for sufficient balance
        if (senderAccount.bankBalance < amount) {
            return "Insufficient Balance"
        }

        def transaction
        try {
            // Deduct amount from sender's account
            senderAccount.bankBalance -= amount
            senderAccount.save(flush: true)

            // Add amount to receiver's account
            receiverAccount.bankBalance += amount
            receiverAccount.save(flush: true)

            // Create transaction record
            transaction = new TransactionManagement(
                    sender: sender,
                    receiver: receiver,
                    sourceAccount: senderAccount,
                    destinationAccount: receiverAccount,
                    amount: amount,
                    transactionDate: new Date(),
                    status: 'Completed',
                    transactionId: UUID.randomUUID().toString()
            )
            transaction.save(flush: true)
        } catch (Exception e) {
            transaction?.status = 'Failed'
            transaction?.save(flush: true)
            throw e
        }

        // Return transaction details
        return new TransactionModel(
                transactionId: transaction.transactionId,
                amount: transaction.amount,
                senderMobileNumber: senderMobileNumber,
                receiverMobileNumber: receiverMobileNumber,
                transactionDate: transaction.transactionDate,
                status: transaction.status
        )
    }





    @Transactional
    def getTransactionHistory(Long phoneNumber) {
        def transactions = TransactionManagement.createCriteria().list {
            or {
                sender {
                    eq("phoneNumber", phoneNumber)
                }
                receiver {
                    eq("phoneNumber", phoneNumber)
                }
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        def transactionHistory = transactions.collect { transaction ->
            new TransactionModel(
                    senderMobileNumber: transaction.sender.phoneNumber,
                    receiverMobileNumber: transaction.receiver?.phoneNumber,
                    amount: transaction.amount,
                    transactionId: transaction.transactionId,
                    transactionDate: formatter.format(new Date(transaction.transactionDate.time)), // Convert to formatted String
                    status: transaction.status
            )
        }
        return transactionHistory
    }


}