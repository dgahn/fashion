package me.dgahn.infrastructure.repository

import me.dgahn.infrastructure.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findByBrand(brand: String): List<ProductEntity>

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

    @Query(
        """
            SELECT new me.dgahn.infrastructure.repository.BrandPriceDto(p.brand, SUM(p.price))
              FROM ProductEntity p
          GROUP BY p.brand
        """,
    )
    fun getBrandTotal(): List<BrandPriceDto>

    @Query(
        """
        SELECT p
          FROM ProductEntity p
         WHERE p.category = :category
           AND p.price = (SELECT MIN(p2.price)
                            FROM ProductEntity p2
                           WHERE p2.category = :category)
    """,
    )
    fun getMinProductsByCategory(
        @Param("category") category: String,
    ): List<ProductEntity>

    @Query(
        """
        SELECT p
          FROM ProductEntity p
         WHERE p.category = :category
           AND p.price = (SELECT MAX(p2.price)
                            FROM ProductEntity p2
                           WHERE p2.category = :category)
    """,
    )
    fun getMaxProductsByCategory(
        @Param("category") category: String,
    ): List<ProductEntity>
}
