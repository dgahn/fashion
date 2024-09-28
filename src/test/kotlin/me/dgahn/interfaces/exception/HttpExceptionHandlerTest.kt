package me.dgahn.interfaces.exception

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.exception.BadRequestException
import me.dgahn.application.service.ProductSearchService
import me.dgahn.domain.service.ProductCreator
import me.dgahn.domain.service.ProductDeleter
import me.dgahn.domain.service.ProductUpdater
import me.dgahn.interfaces.controller.ProductController
import me.dgahn.interfaces.controller.ProductFixture
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

@DisplayName("HttpExceptionHandler 테스트")
@WebMvcTest(HttpExceptionHandler::class, ProductController::class)
@AutoConfigureRestDocs
class HttpExceptionHandlerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var productCreator: ProductCreator

    @MockkBean
    lateinit var productUpdater: ProductUpdater

    @MockkBean
    lateinit var productDeleter: ProductDeleter

    @MockkBean
    lateinit var productSearchService: ProductSearchService

    @DisplayName("BadRequestException 예외 테스트")
    @Test
    fun `BadRequestException 예외가 발생하면 예외 응답을 낼 수 있다`() {
        val url = "api/v1/products/{product-id}"
        val id = 1

        every { productUpdater.update(any()) } throws BadRequestException("예외 발생")

        val pathParams = listOf(
            RequestDocumentation.parameterWithName("product-id").description("상품의 식별자"),
        )
        val requestFields = listOf(
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("price").description("상품의 가격"),
        )
        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("status").description("http 상태 코드"),
            PayloadDocumentation.fieldWithPath("message").description("에러 메시지"),
            PayloadDocumentation.fieldWithPath("timestamp").description("에러가 발생한 시간"),
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
                    .isBadRequest(),
            ).andDo(
                MockMvcRestDocumentation.document(
                    url,
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(pathParams),
                    PayloadDocumentation.requestFields(requestFields),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
    }
}
