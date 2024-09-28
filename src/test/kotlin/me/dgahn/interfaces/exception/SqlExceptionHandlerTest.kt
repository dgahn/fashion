package me.dgahn.interfaces.exception

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.ProductCreator
import me.dgahn.application.service.ProductDeleter
import me.dgahn.application.service.ProductUpdater
import me.dgahn.interfaces.controller.ProductController
import me.dgahn.interfaces.controller.ProductFixture
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
import me.dgahn.util.objectMapper
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
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

@DisplayName("SqlExceptionHandler 테스트")
@WebMvcTest(SqlExceptionHandler::class, ProductController::class)
@AutoConfigureRestDocs
class SqlExceptionHandlerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var productCreator: ProductCreator

    @MockkBean
    lateinit var productUpdater: ProductUpdater

    @MockkBean
    lateinit var productDeleter: ProductDeleter

    @DisplayName("JdbcSQLIntegrityConstraintViolationException 예외 테스트")
    @Test
    fun `JdbcSQL 예외가 발생하면 예외 응답을 낼 수 있다`() {
        val url = "api/v1/products"

        every { productCreator.create(any()) } throws
            JdbcSQLIntegrityConstraintViolationException("", "", "", 0, RuntimeException(), "")

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
                    .post("/$url")
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
                    PayloadDocumentation.requestFields(requestFields),
                    PayloadDocumentation.responseFields(responseFields),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
    }
}
