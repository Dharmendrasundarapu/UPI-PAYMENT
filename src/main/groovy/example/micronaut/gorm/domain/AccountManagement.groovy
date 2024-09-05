package example.micronaut.gorm.domain
import grails.gorm.annotation.Entity

@Entity
class AccountManagement {

    String bankName
    BigDecimal accountNumber
    int pin
    BigDecimal bankBalance = 100000
    int upiPin
    BigDecimal transactionLimit = 100
    Boolean isPrimary=false


    static belongsTo = [user: UserManagement]

    static mapping = {
        id generator: 'increment'
        accountNumber column: 'accNum'
        isPrimary column: 'is_primary'

    }

    static constraints = {
        accountNumber nullable: false, unique: true
        pin unique: true
        transactionLimit min: 0.0
        isPrimary nullable: false

    }
}
