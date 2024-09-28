package me.dgahn.application.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductSearchService(
    private val productRepository: ProductRepository,
) {
    @Transactional(readOnly = true)
    fun searchPriceRange(category: String): Pair<List<Product>, List<Product>> {
        val min = productRepository.getMinProductsByCategory(category).map { it.toDomain() }
        val max = productRepository.getMaxProductsByCategory(category).map { it.toDomain() }
        return min to max
    }
}
