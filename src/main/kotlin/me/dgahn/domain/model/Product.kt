package me.dgahn.domain.model

data class Product(
    val id: Long = 0,
    val brand: String,
    val category: String,
    val price: Int,
)
