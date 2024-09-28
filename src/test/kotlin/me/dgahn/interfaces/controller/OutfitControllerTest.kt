package me.dgahn.interfaces.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.dgahn.application.service.OutfitService
import me.dgahn.interfaces.restdoc.AbstractRestDocControllerTest
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

@DisplayName("OutfitController 테스트")
@WebMvcTest(OutfitController::class)
@AutoConfigureRestDocs
class OutfitControllerTest : AbstractRestDocControllerTest() {
    @MockkBean
    lateinit var outfitService: OutfitService

    @DisplayName("[GET] api/v1/outfit/lowest-price")
    @Test
    fun `카테고리별 가장 저렴한 코디 스타일을 조회할 수 있다`() {
        val url = "api/v1/outfit/lowest-price"
        val documentId = "get/$url"

        every { outfitService.getLowestOutfit() } returns listOf(ProductFixture.DOMAIN)

        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("total").description("코디된 금액의 총합"),
            PayloadDocumentation.fieldWithPath("items[].id").description("상품의 식별자"),
            PayloadDocumentation.fieldWithPath("items[].brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("items[].category").description("상품의 카테고리"),
            PayloadDocumentation.fieldWithPath("items[].price").description("상품의 가격"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url")
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
                            .tag("Outfit API")
                            .summary("카테고리별 가장 저렴한 코디 스타일 조회 API")
                            .description("브랜드 상관 없이 카테고리별 가장 저렴한 스타일의 코드 목록을 조회합니다.")
                            .responseFields(responseFields)
                            .build(),
                    ),
                ),
            )

        result
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
    }

    @DisplayName("[GET] api/v1/outfit/single-brand/lowest-price")
    @Test
    fun `단일브랜드별 가장 저렴한 코디 스타일을 조회할 수 있다`() {
        val url = "api/v1/outfit/single-brand/lowest-price"
        val documentId = "get/$url"

        every { outfitService.getSingleBrandOutfitLowestPrice() } returns listOf(ProductFixture.DOMAIN)

        val responseFields = listOf(
            PayloadDocumentation.fieldWithPath("brand").description("상품의 브랜드"),
            PayloadDocumentation.fieldWithPath("total").description("총 비용"),
            PayloadDocumentation.fieldWithPath("categories[].category").description("카테고리"),
            PayloadDocumentation.fieldWithPath("categories[].price").description("상품 가격"),
        )

        val result = mockMvc
            .perform(
                RestDocumentationRequestBuilders
                    .get("/$url")
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
                            .tag("Outfit API")
                            .summary("단일 브랜드별 가장 저렴한 코디 스타일 조회 API")
                            .description("단일 브랜드별로 가장 저렴하게 코디할 수 있는 스타일을 조회합니다.")
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
