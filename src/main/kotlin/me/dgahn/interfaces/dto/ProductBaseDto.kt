package me.dgahn.interfaces.dto

import me.dgahn.domain.model.Product
import org.springframework.data.jpa.domain.AbstractPersistable_.id

data class ProductRequestDto(
    val brand: String,
    val category: String,
    val price: Int,
) {
    fun toDomain(): Product =
        Product(
            brand = brand,
            category = category,
            price = price,
        )
}

data class ProductResponseDto(
    val id: Long,
    val brand: String,
    val category: String,
    val price: Int,
)

fun Product.toResponse(): ProductResponseDto =
    ProductResponseDto(
        id = id,
        brand = brand,
        category = category,
        price = price,
    )
