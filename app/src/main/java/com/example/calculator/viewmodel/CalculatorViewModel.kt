package com.example.calculator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.data.repository.ExchangeRateRepository
import com.example.calculator.domain.InputValidator
import com.example.calculator.domain.ValidationResult
import com.example.calculator.domain.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CalculatorViewModel(
    private val repository: ExchangeRateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        fetchRates()
    }

    fun onCountrySelected(country: Country) {
        _uiState.update { it.copy(selectedCountry = country) }
        fetchRates()
    }

    fun refreshRates() {
        fetchRates()
    }

    fun onAmountChanged(input: String) {
        _uiState.update { it.copy(amountInput = input, inputError = null) }
        recalculate()
    }

    private fun fetchRates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, networkError = null) }
            try {
                val rates = repository.getExchangeRates()
                _uiState.update { it.copy(exchangeRates = rates, isLoading = false) }
                recalculate()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, networkError = e.message ?: "네트워크 오류가 발생했습니다.")
                }
            }
        }
    }

    private fun recalculate() {
        val state = _uiState.value
        when (val result = InputValidator.validate(state.amountInput)) {
            is ValidationResult.Empty -> {
                _uiState.update { it.copy(conversionResult = null, inputError = null) }
            }

            is ValidationResult.Invalid -> {
                _uiState.update { it.copy(conversionResult = null, inputError = "송금액이 바르지 않습니다") }
            }

            is ValidationResult.Valid -> {
                val rate = state.currentRate
                if (rate == null) {
                    _uiState.update { it.copy(conversionResult = null) }
                    return
                }
                val formatted = NumberFormat.getNumberInstance(Locale.US).apply {
                    minimumFractionDigits = 2
                    maximumFractionDigits = 2
                }.format(result.amount * rate)
                _uiState.update { it.copy(conversionResult = formatted, inputError = null) }
            }
        }
    }
}
