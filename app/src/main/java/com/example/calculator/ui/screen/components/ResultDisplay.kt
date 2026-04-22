package com.example.calculator.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResultDisplay(
    result: String?,
    errorMessage: String?,
    currencyCode: String,
    modifier: Modifier = Modifier
) {
    when {
        errorMessage != null -> Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        )
        result != null -> Text(
            text = "수취금액은 $result $currencyCode 입니다.",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}
