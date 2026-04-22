package com.example.calculator.data.model

data class CurrencyResponse(
    val success: Boolean,
    val quotes: Map<String, Double>
)