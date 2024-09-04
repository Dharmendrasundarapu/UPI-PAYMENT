package example.micronaut.gorm.model

import java.text.SimpleDateFormat

class TransactionModel {
    String senderMobileNumber
    String receiverMobileNumber
    BigDecimal amount
    String upiPin
    String transactionId
    String transactionDate
    String status

    static constraints = {
        senderMobileNumber nullable: false
        receiverMobileNumber nullable: false
        amount nullable: false, min: 1.0
        upiPin nullable: false

        // Response fields are optional in the request
        transactionId nullable: true
        transactionDate nullable: true
        status nullable: true
    }
    private String formatTimestamp(Long timestamp) {
        Date date = new Date(timestamp)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date)
    }
}
