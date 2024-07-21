package com.example.currencies.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencies.service.DefaultRatesService
import com.example.currencies.ui.theme.CurrenciesTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal

@Preview(showBackground = true)
@Composable
fun Form(viewModel: GlobalViewModel = viewModel()) {
    CurrenciesTheme {
        val scope = rememberCoroutineScope()
        val service = DefaultRatesService()
        var currencies by remember { mutableStateOf<Set<String>>(emptySet()) }
        var sum by remember { mutableStateOf("0") }
        var base by remember { mutableStateOf("RUB") }
        var quote by remember { mutableStateOf("USD") }
        var result by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            currencies = scope.async {
                service.getCurrencies(base)
            }.await()
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            Alignment.Center
        ) {
            Column {
                TextField(
                    value = sum,
                    onValueChange = { input -> sum = input.filter { it.isDigit() } },
                    label = { Text("Сумма") },
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Dropdown(
                    "Базовая валюта",
                    currencies,
                    base,
                    { value -> base = value },
                    Modifier.padding(vertical = 16.dp)
                )
                Dropdown(
                    "Котируемая валюта",
                    currencies,
                    quote,
                    { value -> quote = value },
                    Modifier.padding(vertical = 16.dp)
                )
                Text(result)

                Row {
                    Button({
                        scope.launch {
                            if (sum.isBlank()) return@launch

                            val rate = service.convert(BigDecimal(sum), base, quote)
                            result = "Курс $base/$quote: $rate"
                        }
                    }) { Text(text = "Конвертировать") }

                    Button({
                        scope.launch { result = "" }
                    }) { Text(text = "Очистить") }
                }
            }
        }
    }
}
