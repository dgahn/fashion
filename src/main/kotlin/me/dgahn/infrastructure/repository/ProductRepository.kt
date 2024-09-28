package me.dgahn.infrastructure.repository

import me.dgahn.infrastructure.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    @Query(
        """
        SELECT p
          FROM ProductEntity p
         WHERE p.price = (
                SELECT MIN(p2.price)
                  FROM ProductEntity p2
                 WHERE p2.category = p.category)
    """,
    )
    fun cheapestByCategory(): List<ProductEntity>
}
