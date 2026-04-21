package com.example.calculator.viewmodel

import com.example.calculator.domain.model.Country
import java.text.NumberFormat
import java.util.Locale

private val rateFormatter = NumberFormat.getNumberInstance(Locale.US).apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}

data class CalculatorUiState(
    val selectedCountry: Country = Country.KOREA,
    val amountInput: String = "",
    val exchangeRates: Map<Country, Double> = emptyMap(),
    val isLoading: Boolean = false,
    val networkError: String? = null,
    val conversionResult: String? = null,
    val inputError: String? = null
) {
    val currentRate: Double?
        get() = exchangeRates[selectedCountry]

    val rateDisplayText: String
        get() = currentRate?.let {
            "1 USD = ${rateFormatter.format(it)} ${selectedCountry.currencyCode}"
        } ?: ""
}
