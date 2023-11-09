package com.example.swipebox.application.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swipebox.application.screens.exchangeRate.repository.ExchangeRateRepository
import com.example.swipebox.application.screens.exchangeRate.viewModel.ExchangeRateViewModel

class SwipeBoxViewModelFactory constructor(private val repository: ExchangeRateRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ExchangeRateViewModel::class.java)) {
            ExchangeRateViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}