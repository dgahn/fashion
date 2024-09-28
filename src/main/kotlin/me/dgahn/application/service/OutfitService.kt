package me.dgahn.application.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OutfitService(
    private val productRepository: ProductRepository,
) {
    @Transactional(readOnly = true)
    fun getLowestOutfit(): List<Product> {
        return productRepository
            .cheapestByCategory()
            .groupBy { it.category }
            .mapNotNull { it.value.firstOrNull() }
            .map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    fun getSingleBrandOutfitLowestPrice(): List<Product> {
        return productRepository
            .findByBrand(findLowestBrand())
            .map { it.toDomain() }
    }

    private fun findLowestBrand(): String = productRepository
        .getBrandTotal()
        .minBy { it.totalPrice }
        .brand
}
