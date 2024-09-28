package me.dgahn.interfaces.dto

import me.dgahn.domain.model.Product

data class SingleBrandOutfitLowestPriceDto(
    val brand: String,
    val categories: List<CategoryPrice>,
    val total: Int,
) {
    data class CategoryPrice(
        val category: String,
        val price: Int,
    )

    companion object {
        fun of(products: List<Product>): SingleBrandOutfitLowestPriceDto {
            return SingleBrandOutfitLowestPriceDto(
                brand = products.first().brand,
                categories = products.map {
                    CategoryPrice(
                        category = it.category,
                        price = it.price,
                    )
                },
                total = products.sumOf { it.price },
            )
        }
    }
}
