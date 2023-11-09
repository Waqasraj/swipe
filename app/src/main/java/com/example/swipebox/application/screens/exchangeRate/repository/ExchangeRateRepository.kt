package com.example.swipebox.application.screens.exchangeRate.repository

import com.example.swipebox.application.network.services.RetrofitService
import com.example.swipebox.application.network.states.NetworkState
import com.example.swipebox.application.screens.exchangeRate.models.ExchangeRate

class ExchangeRateRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getRates(): NetworkState<ExchangeRate> {
        val response = retrofitService.getRates()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

}