package me.dgahn.interfaces.controller

import me.dgahn.domain.model.Product
import me.dgahn.interfaces.dto.ProductRequestDto

object ProductFixture {
    val DOMAIN = Product(
        brand = "A",
        category = "상의",
        price = 11200,
    )
    val REQUEST = ProductRequestDto(
        brand = "A",
        category = "상의",
        price = 11200,
    )
}
