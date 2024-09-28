package me.dgahn.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import me.dgahn.domain.model.Product

@Entity
@Table(
    name = "product",
    uniqueConstraints = [UniqueConstraint(columnNames = ["brand", "category"])],
)
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false, nullable = false)
    val id: Long = 0,
    @Column(name = "brand")
    val brand: String,
    @Column(name = "category")
    val category: String,
    @Column(name = "price")
    val price: Int,
) {
    fun toDomain(): Product {
        return Product(
            id = this.id,
            brand = this.brand,
            category = this.category,
            price = this.price,
        )
    }
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        brand = brand,
        category = category,
        price = price,
    )
}
