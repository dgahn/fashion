package me.dgahn.util

import me.dgahn.infrastructure.entity.ProductEntity
import me.dgahn.infrastructure.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SampleInsertRunner(
    private val productRepository: ProductRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        productRepository.saveAll(createProducts())
    }

    fun createProducts(): List<ProductEntity> {
        return listOf(
            // 브랜드 A
            ProductEntity(brand = "A", category = "상의", price = 11200),
            ProductEntity(brand = "A", category = "아우터", price = 5500),
            ProductEntity(brand = "A", category = "바지", price = 4200),
            ProductEntity(brand = "A", category = "스니커즈", price = 9000),
            ProductEntity(brand = "A", category = "가방", price = 2000),
            ProductEntity(brand = "A", category = "모자", price = 1700),
            ProductEntity(brand = "A", category = "양말", price = 1800),
            ProductEntity(brand = "A", category = "액세서리", price = 2300),
            // 브랜드 B
            ProductEntity(brand = "B", category = "상의", price = 10500),
            ProductEntity(brand = "B", category = "아우터", price = 5900),
            ProductEntity(brand = "B", category = "바지", price = 3800),
            ProductEntity(brand = "B", category = "스니커즈", price = 9100),
            ProductEntity(brand = "B", category = "가방", price = 2100),
            ProductEntity(brand = "B", category = "모자", price = 2000),
            ProductEntity(brand = "B", category = "양말", price = 2000),
            ProductEntity(brand = "B", category = "액세서리", price = 2200),
            // 브랜드 C
            ProductEntity(brand = "C", category = "상의", price = 10000),
            ProductEntity(brand = "C", category = "아우터", price = 6200),
            ProductEntity(brand = "C", category = "바지", price = 3300),
            ProductEntity(brand = "C", category = "스니커즈", price = 9200),
            ProductEntity(brand = "C", category = "가방", price = 2200),
            ProductEntity(brand = "C", category = "모자", price = 1900),
            ProductEntity(brand = "C", category = "양말", price = 2200),
            ProductEntity(brand = "C", category = "액세서리", price = 2100),
            // 브랜드 D
            ProductEntity(brand = "D", category = "상의", price = 10100),
            ProductEntity(brand = "D", category = "아우터", price = 5100),
            ProductEntity(brand = "D", category = "바지", price = 3000),
            ProductEntity(brand = "D", category = "스니커즈", price = 9500),
            ProductEntity(brand = "D", category = "가방", price = 2500),
            ProductEntity(brand = "D", category = "모자", price = 1500),
            ProductEntity(brand = "D", category = "양말", price = 2400),
            ProductEntity(brand = "D", category = "액세서리", price = 2000),
            // 브랜드 E
            ProductEntity(brand = "E", category = "상의", price = 10700),
            ProductEntity(brand = "E", category = "아우터", price = 5000),
            ProductEntity(brand = "E", category = "바지", price = 3800),
            ProductEntity(brand = "E", category = "스니커즈", price = 9900),
            ProductEntity(brand = "E", category = "가방", price = 2300),
            ProductEntity(brand = "E", category = "모자", price = 1800),
            ProductEntity(brand = "E", category = "양말", price = 2100),
            ProductEntity(brand = "E", category = "액세서리", price = 2100),
            // 브랜드 F
            ProductEntity(brand = "F", category = "상의", price = 11200),
            ProductEntity(brand = "F", category = "아우터", price = 7200),
            ProductEntity(brand = "F", category = "바지", price = 4000),
            ProductEntity(brand = "F", category = "스니커즈", price = 9300),
            ProductEntity(brand = "F", category = "가방", price = 2100),
            ProductEntity(brand = "F", category = "모자", price = 1600),
            ProductEntity(brand = "F", category = "양말", price = 2300),
            ProductEntity(brand = "F", category = "액세서리", price = 1900),
            // 브랜드 G
            ProductEntity(brand = "G", category = "상의", price = 10500),
            ProductEntity(brand = "G", category = "아우터", price = 5800),
            ProductEntity(brand = "G", category = "바지", price = 3900),
            ProductEntity(brand = "G", category = "스니커즈", price = 9000),
            ProductEntity(brand = "G", category = "가방", price = 2200),
            ProductEntity(brand = "G", category = "모자", price = 1700),
            ProductEntity(brand = "G", category = "양말", price = 2100),
            ProductEntity(brand = "G", category = "액세서리", price = 2000),
            // 브랜드 H
            ProductEntity(brand = "H", category = "상의", price = 10800),
            ProductEntity(brand = "H", category = "아우터", price = 6300),
            ProductEntity(brand = "H", category = "바지", price = 3100),
            ProductEntity(brand = "H", category = "스니커즈", price = 9700),
            ProductEntity(brand = "H", category = "가방", price = 2100),
            ProductEntity(brand = "H", category = "모자", price = 1600),
            ProductEntity(brand = "H", category = "양말", price = 2000),
            ProductEntity(brand = "H", category = "액세서리", price = 2000),
            // 브랜드 I
            ProductEntity(brand = "I", category = "상의", price = 11400),
            ProductEntity(brand = "I", category = "아우터", price = 6700),
            ProductEntity(brand = "I", category = "바지", price = 3200),
            ProductEntity(brand = "I", category = "스니커즈", price = 9500),
            ProductEntity(brand = "I", category = "가방", price = 2400),
            ProductEntity(brand = "I", category = "모자", price = 1700),
            ProductEntity(brand = "I", category = "양말", price = 1700),
            ProductEntity(brand = "I", category = "액세서리", price = 2400),
        )
    }
}
