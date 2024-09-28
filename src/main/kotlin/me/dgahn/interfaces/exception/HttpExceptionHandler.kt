package me.dgahn.interfaces.exception

import me.dgahn.application.exception.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class HttpExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST,
                    message = e.message.orEmpty(),
                ),
            )
    }
}
