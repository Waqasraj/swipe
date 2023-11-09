package com.example.swipebox.application.screens.exchangeRate.models

import com.google.gson.annotations.SerializedName

data class ConversionRates (
    @SerializedName("USD") var USD : Double? = null,
    @SerializedName("AED") var AED : Double? = null,
    @SerializedName("AFN") var AFN : Double? = null,
)