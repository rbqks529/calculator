package com.example.calculator.data.repository

import com.example.calculator.domain.model.Country

interface ExchangeRateRepository {
    suspend fun getExchangeRates(): Map<Country, Double>
}
