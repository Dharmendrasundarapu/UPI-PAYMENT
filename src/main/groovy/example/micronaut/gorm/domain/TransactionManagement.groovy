package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity

@Entity
class TransactionManagement {

    BigDecimal amount
    Date transactionDate
    String status
    String transactionId

    // Define explicit fields for sender and sourceAccount
    UserManagement sender
    AccountManagement sourceAccount

    // Define explicit field for receiver and destinationAccount
    UserManagement receiver
    AccountManagement destinationAccount

    static mapping = {
        id generator: 'increment'
        transactionId generator: 'uuid' // Optional: UUID for unique transactionId
    }

    static constraints = {
        amount nullable: false, min: 0.01 // Ensure amount is positive
        transactionDate nullable: false
        status nullable: false, inList: ["Completed", "Pending", "Failed"] // Example statuses
        transactionId nullable: false, unique: true
    }
}
