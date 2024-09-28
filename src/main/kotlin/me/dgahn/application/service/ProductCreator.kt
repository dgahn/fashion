package me.dgahn.application.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.entity.toEntity
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCreator(
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun create(product: Product): Product {
        return productRepository.save(product.toEntity()).toDomain()
    }
}
