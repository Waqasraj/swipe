package com.example.swipebox.application.screens.exchangeRate.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipebox.application.network.states.NetworkState
import com.example.swipebox.application.screens.exchangeRate.models.ExchangeRate
import kotlinx.coroutines.*
import com.example.swipebox.application.screens.exchangeRate.repository.ExchangeRateRepository

class ExchangeRateViewModel constructor(private val mainRepository: ExchangeRateRepository) :
    ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    private val errorMessage: LiveData<String>
        get() = _errorMessage

    val exchangeRate = MutableLiveData<ExchangeRate>()
    var amount = MutableLiveData<Double>(1.0)
    var job: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllRates() {
        viewModelScope.launch {
            when (val response = mainRepository.getRates()) {
                is NetworkState.Success -> {
                    setLoading(false)
                    exchangeRate.postValue(response.data)
                }
                is NetworkState.Error -> {
                    onError(response.response.message())
                }
            }
        }
    }

    private fun setLoading(value: Boolean) {
        loading.value = value
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        setLoading(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun setAmount(s: String) {
        amount.value = s.toDouble();
        // amount = Double(s);
    }

    fun getRatesAgainstAmount(rate: Double): String {
        return (this.amount.value?.times(rate)).toString();
    }

    fun getRatesAgainstSelectedCurrency(rate: Double): String {
        return (this.amount.value?.div(rate)).toString();
    }


}