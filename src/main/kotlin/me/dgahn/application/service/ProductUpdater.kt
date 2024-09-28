package me.dgahn.application.service

import me.dgahn.domain.model.Product
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductUpdater {
    @Transactional
    fun update(product: Product): Product {
        TODO()
    }
}
