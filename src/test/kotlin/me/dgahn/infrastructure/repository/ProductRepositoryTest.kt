package me.dgahn.infrastructure.repository

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @Test
    fun `카테고리별 가장 싼 상품 목록을 조회할 수 있다`() {
        val products = productRepository.cheapestByCategory()
        products.find { it.category == "상의" }?.also {
            it.brand shouldBe "C"
            it.price shouldBe 10000
        }
        products.find { it.category == "아우터" }?.also {
            it.brand shouldBe "E"
            it.price shouldBe 5000
        }
        products.find { it.category == "바지" }?.also {
            it.brand shouldBe "D"
            it.price shouldBe 3000
        }
        products.find { it.category == "스니커즈" }?.also {
            it.brand shouldBe "A"
            it.price shouldBe 9000
        }
        products.find { it.category == "가방" }?.also {
            it.brand shouldBe "A"
            it.price shouldBe 2000
        }
        products.find { it.category == "모자" }?.also {
            it.brand shouldBe "D"
            it.price shouldBe 1500
        }
        products.find { it.category == "양말" }?.also {
            it.brand shouldBe "I"
            it.price shouldBe 1700
        }
        products.find { it.category == "액세서리" }?.also {
            it.brand shouldBe "F"
            it.price shouldBe 1900
        }
    }
}
