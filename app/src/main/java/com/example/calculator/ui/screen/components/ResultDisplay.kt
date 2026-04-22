package com.example.calculator.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultDisplay(
    result: String?,
    errorMessage: String?,
    currencyCode: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        when {
            errorMessage != null -> Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
            result != null -> Text(
                text = "수취금액은 $result $currencyCode 입니다.",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
