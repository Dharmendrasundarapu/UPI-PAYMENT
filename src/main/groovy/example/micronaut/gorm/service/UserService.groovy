package example.micronaut.gorm.service

import example.micronaut.gorm.ApplicationConstants.Constants
import example.micronaut.gorm.domain.UserManagement
import example.micronaut.gorm.handlers.UserNotFound
import example.micronaut.gorm.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton


@Singleton
class UserService {



    @Transactional
    def createUser(UserModel userModel)
    {
        UserManagement userDomain=userModel.toUser(userModel)
        userDomain.save()

        return  userDomain
    }
    @Transactional
    def getDetails(Long phoneNumber,String password)
    {
        UserManagement userDomain=UserManagement.findByPhoneNumberAndPassword(phoneNumber,password)
        if(userDomain)
        {
            return  UserModel.fromUserDomain(userDomain)
        }
        else
        {
            throw  new  UserNotFound(Constants.USER_NOT_FOUND)
        }
    }
    @Transactional
    def updateUser(Long id,UserModel updatedUserModel)
    {
        if(updatedUserModel==null)
        {
            return "Invalid data"
        }
        UserManagement userDomain=UserManagement.get(id)
        if(UserManagement==null)
        {
            return "No user"
        }
        userDomain.firstName=updatedUserModel.firstName
        userDomain.lastName=updatedUserModel.lastName
        userDomain.address=updatedUserModel.address
        userDomain.phoneNumber=updatedUserModel.phoneNumber
        userDomain.password=updatedUserModel.password
        userDomain.save()

        return userDomain
    }
    @Transactional
    def userById(Long id)
    {
        UserManagement userDomain=UserManagement.get(id)
        if(userDomain)
        {
            return UserModel.fromUserDomain(userDomain)
        }
        else {
            throw new UserNotFound(Constants.USER_NOT_FOUND)
        }
    }
    @Transactional
    def deleteUser(Long id)
    {
        UserManagement userDomain=UserManagement.get(id)
        if(userDomain)
        {
            userDomain.delete()
            return true
        }
        else
        {
            return "No user with ${id}"
        }
    }



}
