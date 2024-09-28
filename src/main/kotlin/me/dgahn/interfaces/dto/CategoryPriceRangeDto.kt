package me.dgahn.interfaces.dto

import me.dgahn.domain.model.Product

data class PriceRangeResponse(
    val category: String,
    val min: List<PriceDetail>,
    val max: List<PriceDetail>,
) {
    companion object {
        fun of(category: String, products: List<Product>): PriceRangeResponse {
            return PriceRangeResponse(
                category = category,
                min = minPriceDetail(products),
                max = maxPriceDetail(products),
            )
        }

        private fun minPriceDetail(products: List<Product>) = products
            .filter { it.price == products.minOf { it.price } }
            .map { it.toDetail() }

        private fun maxPriceDetail(products: List<Product>) = products
            .filter { it.price == products.maxOf { it.price } }
            .map { it.toDetail() }
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
