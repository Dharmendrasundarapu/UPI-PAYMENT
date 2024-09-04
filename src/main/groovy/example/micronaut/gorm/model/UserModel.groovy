package example.micronaut.gorm.model


import example.micronaut.gorm.domain.UserManagement

class UserModel {
    Long id
    String firstName
    String lastName
    String address
    Long phoneNumber
    String email
    String password
    static  UserModel fromUserDomain(UserManagement userDomain)
    {
        if (userDomain==null)
        {
            return  null
        }
        return  new UserModel(
                id:userDomain.id,
                firstName: userDomain.firstName,
                lastName: userDomain.lastName,
                address: userDomain.address,
                phoneNumber: userDomain.phoneNumber,
                email: userDomain.email
        )
    }

    static  UserManagement toUser(UserModel userModel)
    {
        if(userModel==null)
        {
            return  null
        }
        UserManagement userDomain=new UserManagement()
          userDomain.firstName=userModel.firstName
        userDomain.lastName=userModel.lastName
        userDomain.address=userModel.address
         userDomain.phoneNumber=userModel.phoneNumber
        userDomain.email=userModel.email
        userDomain.password=userModel.password

        return  userDomain

    }
}
