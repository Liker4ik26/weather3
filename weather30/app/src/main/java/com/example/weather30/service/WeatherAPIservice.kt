package com.example.weather30.service

import com.example.weather30.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/weather?q=Omsk&units=metric&appid=34ae534ae4ff9829f963590e080ea518
class WeatherAPIservice {
    private val BASE_URL ="https://api.openweathermap.org/"
    private  val api =  Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName:String):Single<WeatherModel>{
        return api.getData(cityName)
    }
}