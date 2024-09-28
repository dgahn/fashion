package me.dgahn.application.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class OutfitSearcher(
    private val productRepository: ProductRepository,
) {
    fun getLowestOutfit(): List<Product> {
        TODO()
    }
}
