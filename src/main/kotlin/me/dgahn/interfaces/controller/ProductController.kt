package me.dgahn.interfaces.controller

import me.dgahn.application.service.ProductSearchService
import me.dgahn.domain.service.ProductCreator
import me.dgahn.domain.service.ProductDeleter
import me.dgahn.domain.service.ProductUpdater
import me.dgahn.interfaces.dto.PriceRangeResponse
import me.dgahn.interfaces.dto.ProductRequestDto
import me.dgahn.interfaces.dto.ProductResponseDto
import me.dgahn.interfaces.dto.toResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    private val productDeleter: ProductDeleter,
    private val productSearchService: ProductSearchService,
) {
    @PostMapping("/api/v1/products")
    fun create(
        @RequestBody request: ProductRequestDto,
    ): ResponseEntity<ProductResponseDto> {
        return ResponseEntity.ok(productCreator.create(request.toDomain()).toResponse())
    }

    @GetMapping("/api/v1/products/categories/{category}/price-range")
    fun searchPriceRangeByCategory(
        @PathVariable("category") category: String,
    ): ResponseEntity<PriceRangeResponse> {
        return ResponseEntity.ok(
            PriceRangeResponse.of(
                category = category,
                products = productSearchService.searchPriceRange(category = category),
            ),
        )
    }

    @PutMapping("/api/v1/products/{product-id}")
    fun update(
        @PathVariable("product-id") id: Long,
        @RequestBody request: ProductRequestDto,
    ): ResponseEntity<ProductResponseDto> {
        return ResponseEntity.ok(productUpdater.update(request.toDomain(id = id)).toResponse())
    }

    @DeleteMapping("api/v1/products/{product-id}")
    fun delete(
        @PathVariable("product-id") id: Long,
    ): ResponseEntity<Unit> {
        productDeleter.delete(id)
        return ResponseEntity.ok(Unit)
    }
}
