package com.example.calculator.domain.model

enum class Country(
    val displayName: String,
    val currencyCode: String,
    val apiQuoteKey: String
) {
    KOREA("Korea", "KRW", "USDKRW"),
    JAPAN("Japan", "JPY", "USDJPY"),
    PHILIPPINES("Philippines", "PHP", "USDPHP")
}