package me.dgahn.interfaces.exception

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SqlExceptionHandler {
    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException::class)
    fun handleJdbcSQLIntegrityConstraintViolationException(
        e: JdbcSQLIntegrityConstraintViolationException,
    ): ResponseEntity<ErrorResponse> {
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
