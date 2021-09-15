package com.cultino

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("mandi?lat=28.44108136&lon=77.0526054&ver=89&lang=hi&crop_id=10")
    fun getMovies() : Call<JsonObject>

    companion object {

        var BASE_URL = "https://thekrishi.com/test/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}