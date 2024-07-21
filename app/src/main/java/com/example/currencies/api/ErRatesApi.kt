package com.example.currencies.api

import com.example.currencies.model.ErRateResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigDecimal

class ErRatesApi : RatesApi {

    private val client = OkHttpClient()
    private val gson = Gson()


    override fun getRates(base: String): Map<String, BigDecimal> {
        val request = Request.Builder()
            .url("$BASE_URL/$base")
            .get()
            .build()
        val response = client.newCall(request).execute()
        val body = gson.fromJson(response.body!!.string(), ErRateResponse::class.java)
        return body.rates
    }


    companion object {
        private const val BASE_URL = "https://open.er-api.com/v6/latest"
    }
}