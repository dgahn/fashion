package me.dgahn.domain.service

import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductDeleter(
    private val productSearcher: ProductSearcher,
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun delete(id: Long) {
        productSearcher.getProduct(id)
        productRepository.deleteById(id)
    }
}
