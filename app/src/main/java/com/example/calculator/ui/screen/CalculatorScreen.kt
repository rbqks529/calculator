package com.example.calculator.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.calculator.R
import com.example.calculator.domain.model.Country
import com.example.calculator.ui.screen.components.AmountInputField
import com.example.calculator.ui.screen.components.CountrySelector
import com.example.calculator.ui.screen.components.RateDisplay
import com.example.calculator.ui.screen.components.ResultDisplay
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.viewmodel.CalculatorUiState
import com.example.calculator.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CalculatorContent(
        uiState = uiState,
        onCountrySelected = viewModel::onCountrySelected,
        onAmountChanged = viewModel::onAmountChanged,
        modifier = modifier
    )
}

@Composable
fun CalculatorContent(
    uiState: CalculatorUiState,
    onCountrySelected: (Country) -> Unit,
    onAmountChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
        )

        HorizontalDivider()

        Text(
            text = stringResource(R.string.label_sending_country),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(R.string.sending_country_value),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        CountrySelector(
            selectedCountry = uiState.selectedCountry,
            onCountrySelected = onCountrySelected
        )

        RateDisplay(
            rateText = uiState.rateDisplayText,
            isLoading = uiState.isLoading,
            networkError = uiState.networkError
        )

        AmountInputField(
            value = uiState.amountInput,
            onValueChange = onAmountChanged,
            isError = uiState.inputError != null
        )

        ResultDisplay(
            result = uiState.conversionResult,
            errorMessage = uiState.inputError,
            currencyCode = uiState.selectedCountry.currencyCode
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorContentPreview() {
    CalculatorTheme {
        CalculatorContent(
            uiState = CalculatorUiState(
                selectedCountry = Country.KOREA,
                exchangeRates = mapOf(Country.KOREA to 1340.5),
                conversionResult = "134,050.00"
            ),
            onCountrySelected = {},
            onAmountChanged = {}
        )
    }
}
