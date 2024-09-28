package me.dgahn.interfaces.controller

import me.dgahn.application.service.OutfitService
import me.dgahn.interfaces.dto.OutfitLowestPriceDto
import me.dgahn.interfaces.dto.SingleBrandOutfitLowestPriceDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class OutfitController(
    private val outfitService: OutfitService,
) {
    @GetMapping("/api/v1/outfit/lowest-price")
    fun getLowestPrice(): ResponseEntity<OutfitLowestPriceDto> {
        return ResponseEntity.ok(
            OutfitLowestPriceDto.of(outfitService.getLowestOutfit()),
        )
    }

    @GetMapping("/api/v1/outfit/single-brand/lowest-price")
    fun getSingleBrandLowestPrice(): ResponseEntity<SingleBrandOutfitLowestPriceDto> {
        return ResponseEntity.ok(
            SingleBrandOutfitLowestPriceDto.of(outfitService.getSingleBrandOutfitLowestPrice()),
        )
    }
}
