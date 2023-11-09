package com.example.swipebox.application.screens.exchangeRate


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.swipebox.application.network.services.RetrofitService
import com.example.swipebox.application.screens.exchangeRate.repository.ExchangeRateRepository
import com.example.swipebox.application.screens.exchangeRate.viewModel.ExchangeRateViewModel
import com.example.swipebox.application.viewModelFactory.SwipeBoxViewModelFactory
import com.example.swipebox.databinding.ActivityMainBinding


class ExchangeRateActivity : AppCompatActivity() {

    private lateinit var viewModel: ExchangeRateViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
        viewModel.getAllRates()
    }

    private fun setupObserver() {
        viewModel.loading.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }


        viewModel.exchangeRate.observe(this) {
            if (it.conversionRates != null) {
                binding.main.visibility = View.VISIBLE
                it.conversionRates!!.AED?.let { it1 -> updateValues(it1) }
            }
        }

        viewModel.amount.observe(this) {
            if (viewModel.exchangeRate.value != null) {
                viewModel.exchangeRate.value!!.conversionRates!!.AED?.let { it1 -> updateValues(it1) }

            }
        }
    }

    //we can do recyclerview for all values
    //just showing one for now
    private fun updateValues(rate: Double, amount: Double = 1.0) {
        binding.currencyName.text = "AED"
        binding.exchangeRate.text = viewModel.getRatesAgainstAmount(rate)
        binding.oneEuroRate.text = "1 EUR = $rate"
        binding.oneRate.text = "1 AED = " + viewModel.getRatesAgainstSelectedCurrency(rate)
    }

    private fun setupUI() {
        binding.amount.requestFocus()
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setAmount(s.toString())
            }

            override fun beforeTextChanged(
                c: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(e: Editable) {

            }
        })
    }

    private fun setupViewModel() {
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = ExchangeRateRepository(retrofitService)
        viewModel = ViewModelProvider(
            this,
            SwipeBoxViewModelFactory(mainRepository)
        )[ExchangeRateViewModel::class.java]
    }
}