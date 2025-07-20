package com.example.getrandomquotesapp.data.api

import com.example.getrandomquotesapp.data.response.RandomQuotesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

interface ApiService {
    @GET("quotes/random")
    fun getRandomQuotes(

    ): Call<RandomQuotesResponse>
}