package me.dgahn.application.exception

open class BadRequestException(
    message: String,
) : RuntimeException(message)
