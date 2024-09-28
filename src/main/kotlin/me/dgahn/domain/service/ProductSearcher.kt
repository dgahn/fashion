package me.dgahn.domain.service

import me.dgahn.application.exception.FashionNotFoundException
import me.dgahn.domain.model.Product
import me.dgahn.infrastructure.entity.ProductEntity
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductSearcher(
    private val productRepository: ProductRepository
) {
    fun getProduct(productId: Long): Product {
        return productRepository.findByIdOrNull(productId)?.toDomain()
            ?: throw FashionNotFoundException("Product with ID $productId not found")
    }
}
