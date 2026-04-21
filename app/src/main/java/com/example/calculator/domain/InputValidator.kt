package com.example.calculator.domain

object InputValidator {
    private const val MIN_AMOUNT = 1.0
    private const val MAX_AMOUNT = 10_000.0

    fun validate(input: String): ValidationResult {
        if (input.isBlank()) return ValidationResult.Empty
        val amount = input.toDoubleOrNull() ?: return ValidationResult.Invalid
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) return ValidationResult.Invalid
        return ValidationResult.Valid(amount)
    }
}

sealed class ValidationResult {
    data object Empty : ValidationResult()
    data class Valid(val amount: Double) : ValidationResult()
    data object Invalid : ValidationResult()
}
