package me.dgahn.application.service

import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import me.dgahn.infrastructure.entity.ProductEntity
import me.dgahn.infrastructure.repository.ProductRepository
import org.junit.jupiter.api.Test

class OutfitSearcherTest {
    private val productRepository: ProductRepository = mockk()
    private val outfitSearcher: OutfitSearcher = OutfitSearcher(productRepository)

    @Test
    fun `카테고리에 최소가격이 여러개인 경우 카테고리당 하나만 선택된다`() {
        every { productRepository.cheapestByCategory() } returns listOf(
            ProductEntity(brand = "A", category = "상의", price = 10000),
            ProductEntity(brand = "B", category = "상의", price = 10000),
            ProductEntity(brand = "C", category = "상의", price = 10000),
        )

        outfitSearcher.getLowestOutfit() shouldHaveSize 1
    }
}
