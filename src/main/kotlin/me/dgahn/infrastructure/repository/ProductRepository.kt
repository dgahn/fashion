package me.dgahn.infrastructure.repository

import me.dgahn.infrastructure.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long>
