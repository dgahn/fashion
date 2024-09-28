package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.ProductCreator
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@DisplayName("ProductController 테스트")
@WebMvcTest(ProductController::class)
@AutoConfigureRestDocs
class ProductControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var productCreator: ProductCreator

    @DisplayName("[POST] api/v1/products")
    @Test
    fun `상품을 등록할 수 있다`() {
        val url = "post/api/v1/products"

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
                    url,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(requestFields),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            ).andDo(
                MockMvcRestDocumentation.document(
                    url,
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
}
