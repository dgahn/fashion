package me.dgahn.application.exception

data class FashionNotFoundException(
    override val message: String,
) : BadRequestException(message)
