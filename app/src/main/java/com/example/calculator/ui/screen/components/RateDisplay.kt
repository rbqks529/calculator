package com.example.calculator.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RateDisplay(
    rateText: String,
    isLoading: Boolean,
    networkError: String?,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> CircularProgressIndicator(modifier = modifier)
        networkError != null -> Text(
            text = networkError,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        )
        rateText.isNotEmpty() -> Text(
            text = rateText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}
