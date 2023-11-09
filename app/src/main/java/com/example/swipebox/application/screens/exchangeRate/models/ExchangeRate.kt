package com.example.swipebox.application.screens.exchangeRate.models

import com.google.gson.annotations.SerializedName

data class ExchangeRate(
    @SerializedName("base_code") var baseCode: String? = null,
    @SerializedName("conversion_rates") var conversionRates: ConversionRates? = ConversionRates()
)
