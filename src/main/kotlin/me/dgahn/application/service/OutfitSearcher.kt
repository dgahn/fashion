package me.dgahn.application.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OutfitSearcher(
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

    fun getSingleBrandOutfitLowestPrice(): List<Product> {
        TODO("Not yet implemented")
    }
}
