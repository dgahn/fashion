package me.dgahn.interfaces.dto

import me.dgahn.domain.model.Product

data class PriceRangeResponse(
    val category: String,
    val min: List<PriceDetail>,
    val max: List<PriceDetail>,
) {
    companion object {
        fun of(category: String, products: Pair<List<Product>, List<Product>>): PriceRangeResponse {
            return PriceRangeResponse(
                category = category,
                min = products.first.map { it.toDetail() },
                max = products.second.map { it.toDetail() },
            )
        }
    }
}

data class PriceDetail(
    val brand: String,
    val price: Int,
)

fun Product.toDetail(): PriceDetail {
    return PriceDetail(
        brand = brand,
        price = price,
    )
}
