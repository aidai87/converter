package com.example.currencies.api

import java.math.BigDecimal

interface RatesApi {

    fun getRates(base: String): Map<String, BigDecimal>

}
