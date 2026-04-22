package com.example.calculator.domain.model

enum class Country(
    val displayName: String,
    val currencyCode: String,
    val apiQuoteKey: String
) {
    KOREA("한국", "KRW", "USDKRW"),
    JAPAN("일본", "JPY", "USDJPY"),
    PHILIPPINES("필리핀", "PHP", "USDPHP")
}