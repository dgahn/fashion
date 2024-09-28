package me.dgahn.interfaces.dto

import me.dgahn.domain.model.Product

data class OutfitLowestPriceDto(
    val total: Int,
    val items: List<ProductResponseDto>,
) {
    companion object {
        fun of(products: List<Product>): OutfitLowestPriceDto {
            return OutfitLowestPriceDto(
                total = products.sumOf { it.price },
                items = products.map { it.toResponse() },
            )
        }
    }
}
