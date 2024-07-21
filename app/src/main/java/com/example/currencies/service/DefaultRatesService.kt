package com.example.currencies.service

import com.example.currencies.api.ErRatesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class DefaultRatesService : RatesService {

    private val api: ErRatesApi = ErRatesApi()


    override suspend fun convert(sum: BigDecimal, base: String, quote: String): String {
        return withContext(Dispatchers.IO) {
            val rates = api.getRates(base)
            val rate = rates[quote]!!
            return@withContext (sum * rate).stripTrailingZeros().toString()
        }
    }

    override suspend fun getCurrencies(base: String): Set<String> {
        return withContext(Dispatchers.IO) {
            return@withContext api.getRates(base).keys
        }
    }

}
