package me.dgahn.application.service

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import me.dgahn.infrastructure.entity.ProductEntity
import me.dgahn.infrastructure.repository.BrandPriceDto
import me.dgahn.infrastructure.repository.ProductRepository
import org.junit.jupiter.api.Test

class OutfitServiceTest {
    private val productRepository: ProductRepository = mockk()
    private val outfitService: OutfitService = OutfitService(productRepository)

    @Test
    fun `카테고리에 최소가격이 여러개인 경우 카테고리당 하나만 선택된다`() {
        every { productRepository.cheapestByCategory() } returns listOf(
            ProductEntity(brand = "A", category = "상의", price = 10000),
            ProductEntity(brand = "B", category = "상의", price = 10000),
            ProductEntity(brand = "C", category = "상의", price = 10000),
        )

        outfitService.getLowestOutfit() shouldHaveSize 1
    }

    @Test
    fun `단일 브랜드 최소가격 코디 스타일을 조회할 수 있다`() {
        every { productRepository.getBrandTotal() } returns listOf(
            BrandPriceDto(brand = "A", 45000),
            BrandPriceDto(brand = "B", 37600),
        )
        every { productRepository.findByBrand("B") } returns listOf(
            ProductEntity(brand = "B", category = "상의", price = 10500),
            ProductEntity(brand = "B", category = "아우터", price = 5900),
            ProductEntity(brand = "B", category = "바지", price = 3800),
            ProductEntity(brand = "B", category = "스니커즈", price = 9100),
            ProductEntity(brand = "B", category = "가방", price = 2100),
            ProductEntity(brand = "B", category = "모자", price = 2000),
            ProductEntity(brand = "B", category = "양말", price = 2000),
            ProductEntity(brand = "B", category = "액세서리", price = 2200),
        )
        val actual = outfitService.getSingleBrandOutfitLowestPrice()
        actual shouldHaveSize 8
        actual.first().brand shouldBe "B"
    }
}
