package com.example.calculator.data.repository

import com.example.calculator.data.remote.CurrencyApiService
import com.example.calculator.domain.model.Country

class ExchangeRateRepositoryImpl(
    private val apiService: CurrencyApiService
) : ExchangeRateRepository {

    override suspend fun getExchangeRates(): Map<Country, Double> {
        val response = apiService.getRates()
        if (!response.success) throw Exception("환율 데이터를 가져오지 못했습니다.")
        return Country.entries.associateWith { country ->
            response.quotes[country.apiQuoteKey]
                ?: throw Exception("${country.currencyCode} 환율 정보가 없습니다.")
        }
    }
}
