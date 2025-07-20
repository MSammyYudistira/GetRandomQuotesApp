package com.example.getrandomquotesapp.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getrandomquotesapp.data.api.ApiConfig
import com.example.getrandomquotesapp.data.response.RandomQuotesResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class MainViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()
    val quote = mutableStateOf<RandomQuotesResponse?>(null)

    val errorMessage = mutableStateOf("")

    init {
        fetchRandomQuotes()
    }

    fun fetchRandomQuotes() {
            val call = apiService.getRandomQuotes()
            call.enqueue(object : Callback<RandomQuotesResponse> {
                override fun onResponse(
                    call: Call<RandomQuotesResponse>,
                    response: Response<RandomQuotesResponse>
                ) {
                    if (response.isSuccessful) {
                        quote.value = response.body()
                    } else {
                        errorMessage.value = "Fetch Failed: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<RandomQuotesResponse>, t: Throwable) {
                    errorMessage.value = "Fetch Failed: ${t.message}"
                }

            })
    }

}