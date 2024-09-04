package example.micronaut.gorm.handlers

class UserNotFound extends  RuntimeException{
    UserNotFound(String msg)
    {
        super(msg)
    }
}
