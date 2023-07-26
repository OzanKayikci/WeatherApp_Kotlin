package com.example.weatherapp.di.retrofit

import com.example.weatherapp.models.WeatherForecast
import com.example.weatherapp.models.WeatherInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {
    @GET("data/2.5/weather?appid=ffd8db34301db3b599194fc212c60742&units=metric")
    fun getCurrentWeather(@Query("q") location: String) : Call<WeatherInfo>

    @GET("data/2.5/forecast?appid=ffd8db34301db3b599194fc212c60742&units=metric")
    fun getFiveDayWeather(@Query("q") location: String) : Call<WeatherForecast>

//    @GET("img/wn/{icon}@2x.png")
//    fun getWeatherIcon(@Path("icon") icon:String) : Call<ResponseBody>
}