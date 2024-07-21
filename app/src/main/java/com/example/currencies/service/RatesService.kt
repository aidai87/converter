package com.example.currencies.service

import java.math.BigDecimal

interface RatesService {

    suspend fun convert(sum: BigDecimal, base: String, quote: String): String

    suspend fun getCurrencies(base: String): Set<String>

}