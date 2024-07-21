package com.example.currencies.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GlobalViewModel : ViewModel() {

    private var _currency by mutableStateOf("")
    private var _result by mutableStateOf("")


    var currency: String
        get() = _currency
        set(value) {
            _currency = value
        }

    var result: String
        get() = _result
        set(value) {
            _result = value
        }

}