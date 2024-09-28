package me.dgahn.interfaces.controller

import me.dgahn.application.service.ProductCreator
import me.dgahn.interfaces.dto.ProductRequestDto
import me.dgahn.interfaces.dto.ProductResponseDto
import me.dgahn.interfaces.dto.toResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ProductController(
    private val productCreator: ProductCreator,
) {
    @PostMapping("/api/v1/products")
    fun create(
        @RequestBody request: ProductRequestDto,
    ): ResponseEntity<ProductResponseDto> {
        return ResponseEntity.ok(productCreator.create(request.toDomain()).toResponse())
    }
}
