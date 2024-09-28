package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.ProductSearchService
import me.dgahn.domain.service.ProductCreator
import me.dgahn.domain.service.ProductDeleter
import me.dgahn.domain.service.ProductUpdater
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
import me.dgahn.util.objectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("ProductController 테스트")
@WebMvcTest(ProductController::class)
@AutoConfigureRestDocs
class ProductControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var productCreator: ProductCreator

    @MockkBean
    lateinit var productUpdater: ProductUpdater

    @MockkBean
    lateinit var productDeleter: ProductDeleter

    @MockkBean
    lateinit var productSearchService: ProductSearchService

    @DisplayName("[POST] api/v1/products")
    @Test
    fun `상품을 등록할 수 있다`() {
        val url = "api/v1/products"
        val documentId = "post/$url"

        every { productCreator.create(any()) } returns ProductFixture.DOMAIN

        val requestFields = listOf(
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("price").description("상품의 가격"),
        )
        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("id").description("상품의 식별자"),
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("price").description("상품의 가격"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .post("/$url")
                    .content(objectMapper.writeValueAsString(ProductFixture.REQUEST))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"),
            ).andExpect(
                MockMvcResultMatchers
                    .status()
                    .isOk(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(requestFields),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    ResourceDocumentation.resource(
                        ResourceSnippetParametersBuilder()
                            .tag("상품 API")
                            .summary("상품 등록 API")
                            .description("상품을 등록합니다.")
                            .requestFields(requestFields)
                            .responseFields(responseFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("[GET] api/v1/products/categories/{category}/price-range")
    @Test
    fun `카테고리로 상품의 가격 범위 정보를 조회할 수 있다`() {
        val category = "상의"
        val url = "api/v1/products/categories/{category}/price-range"
        val documentId = "post/$url"

        every { productSearchService.searchPriceRange(category) } returns listOf(ProductFixture.DOMAIN)

        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("min[].brand").description("최소 가격의 상품 브랜드명"),
            PayloadDocumentation.fieldWithPath("min[].price").description("최소 가격의 상품 가격"),
            PayloadDocumentation.fieldWithPath("max[].brand").description("최대 가격의 상품 브랜드명"),
            PayloadDocumentation.fieldWithPath("max[].price").description("최대 가격의 상품 가격"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url", category)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"),
            ).andExpect(
                MockMvcResultMatchers
                    .status()
                    .isOk(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    ResourceDocumentation.resource(
                        ResourceSnippetParametersBuilder()
                            .tag("상품 API")
                            .summary("상품 가격 범위 조회 API")
                            .description("상품 가격에 대한 범위를 카테고리 정보로 조회합니다.")
                            .responseFields(responseFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("[PUT] api/v1/products")
    @Test
    fun `상품을 업데이트할 수 있다`() {
        val url = "api/v1/products/{product-id}"
        val documentId = "put/$url"
        val id = 1

        every { productUpdater.update(any()) } returns ProductFixture.DOMAIN

        val pathParams = listOf(
            RequestDocumentation.parameterWithName("product-id").description("상품의 식별자"),
        )
        val requestFields = listOf(
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("price").description("상품의 가격"),
        )
        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("id").description("상품의 식별자"),
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("price").description("상품의 가격"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .put("/$url", id)
                    .content(objectMapper.writeValueAsString(ProductFixture.REQUEST))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"),
            ).andExpect(
                MockMvcResultMatchers
                    .status()
                    .isOk(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(pathParams),
                    PayloadDocumentation.requestFields(requestFields),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    ResourceDocumentation.resource(
                        ResourceSnippetParametersBuilder()
                            .tag("상품 API")
                            .summary("상품 변경 API")
                            .description("상품을 변경합니다.")
                            .pathParameters(*pathParams.toTypedArray())
                            .requestFields(requestFields)
                            .responseFields(responseFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("[DELETE] api/v1/products")
    @Test
    fun `상품을 삭제할 수 있다`() {
        val url = "api/v1/products/{product-id}"
        val documentId = "delete/$url"
        val id = 1

        every { productDeleter.delete(any()) } returns Unit

        val pathParams = listOf(
            RequestDocumentation.parameterWithName("product-id").description("상품의 식별자"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .delete("/$url", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"),
            ).andExpect(
                MockMvcResultMatchers
                    .status()
                    .isOk(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(pathParams),
                ),
            ).andDo(
                MockMvcRestDocumentation.document(
                    documentId,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    ResourceDocumentation.resource(
                        ResourceSnippetParametersBuilder()
                            .tag("상품 API")
                            .summary("상품 삭제 API")
                            .description("상품을 삭제합니다.")
                            .pathParameters(*pathParams.toTypedArray())
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }
}
