package example.micronaut.gorm.controller


import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject


@Controller("/user")
class UserController {

    @Inject
    UserService userService

    @Post("/register")
    def saveUser(@Body UserModel userModel)
    {
        userService.createUser(userModel)
        return "Registration is Successful"

    }
    @Post("/login")
    def userDetails(@Body UserModel userModel)
    {
        userService.getDetails(userModel.phoneNumber,userModel.password)
        return "Log in Successful"
    }
    @Put("/{id}")
    def userupdate(@PathVariable Long id,@Body UserModel userModel)
    {
        userService.updateUser(id,userModel)
            return "Updated Successfully"


    }
    @Get("/{id}")
    def specUser(@PathVariable Long id)
    {

            def user = userService.userById(id)
            return user

    }
    @Delete("/{id}")
    def userdelete(@PathVariable Long id) {
        userService.deleteUser(id)
        return "Deleted Successfully"


    }
}
