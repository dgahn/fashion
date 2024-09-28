package me.dgahn.application.exception

data class FashionNotFoundException(override val message: String) : RuntimeException(message)
