package me.dgahn.domain.service

import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.entity.toEntity
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductUpdater(
    private val productSearcher: ProductSearcher,
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun update(product: Product): Product {
        productSearcher.getProduct(product.id)
        return productRepository.save(product.toEntity()).toDomain()
    }
}
