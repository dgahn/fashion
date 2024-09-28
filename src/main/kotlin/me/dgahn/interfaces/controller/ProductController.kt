package me.dgahn.interfaces.controller

import me.dgahn.application.service.ProductCreator
import me.dgahn.application.service.ProductUpdater
import me.dgahn.interfaces.dto.ProductRequestDto
import me.dgahn.interfaces.dto.ProductResponseDto
import me.dgahn.interfaces.dto.toResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ProductController(
    private val productCreator: ProductCreator,
    private val productUpdater: ProductUpdater,
) {
    @PostMapping("/api/v1/products")
    fun create(
        @RequestBody request: ProductRequestDto,
    ): ResponseEntity<ProductResponseDto> {
        return ResponseEntity.ok(productCreator.create(request.toDomain()).toResponse())
    }

    @PutMapping("/api/v1/products/{product-id}")
    fun update(
        @PathVariable("product-id") id: Long,
        @RequestBody request: ProductRequestDto,
    ): ResponseEntity<ProductResponseDto> {
        return ResponseEntity.ok(productUpdater.update(request.toDomain(id = id)).toResponse())
    }
}
