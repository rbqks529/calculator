package com.example.calculator.data.remote

import com.example.calculator.BuildConfig
import com.example.calculator.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("live")
    suspend fun getRates(
        @Query("access_key") accessKey: String = BuildConfig.CURRENCY_API_KEY,
        @Query("currencies") currencies: String = "KRW,JPY,PHP",
        @Query("source") source: String = "USD",
        @Query("format") format: Int = 1
    ): CurrencyResponse
}
