package example.micronaut.gorm.handlers

class ErrorResponse {
    Long statusCode
    String error
    String message

    ErrorResponse(Long statusCode, String error, String message) {
        this.statusCode = statusCode
        this.error = error
        this.message = message
    }
}
