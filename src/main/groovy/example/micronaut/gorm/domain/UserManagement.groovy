package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity


@Entity
class UserManagement {
    String firstName
    String lastName
    String address
    Long phoneNumber
    String email
    String password

    static  hasMany = [accounts:AccountManagement]

    static  mapping={
        id generator:'increment'
        phoneNumber column:"phNum"
    }
    static constraints={
        firstName blank:false
        address blank:false
        phoneNumber nullable:false,unique:true
        email blank:false,unique: true
        password blank:false,unique: true
    }


}
