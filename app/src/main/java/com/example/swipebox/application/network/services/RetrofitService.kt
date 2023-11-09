package com.example.swipebox.application.network.services


import com.example.swipebox.application.screens.exchangeRate.models.ExchangeRate
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("latest/EUR")
    suspend fun getRates(): Response<ExchangeRate>

    companion object {
        private var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://v6.exchangerate-api.com/v6/765df4abc2df90fcf953207a/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}