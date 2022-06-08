package com.example.weather30.service

import com.example.weather30.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=Omsk&units=metric&appid=34ae534ae4ff9829f963590e080ea518
interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&appid=34ae534ae4ff9829f963590e080ea518")
    fun getData(
        @Query("q") cityName:String
    ):Single<WeatherModel>
}