package com.example.weatherapp.di.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.models.WeatherForecast
import com.example.weatherapp.models.WeatherInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {

    fun getCurrentWeather(location:String,liveData: MutableLiveData<WeatherInfo>){
        retrofitServiceInstance.getCurrentWeather(location).enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                Log.e("RetrofitError", "Retrofit onFailure: ${t.message}")
               liveData.postValue(null)
            }

        })
    }

//    fun getWeatherIcon(icon:String,liveData: MutableLiveData<String>){
//        retrofitServiceInstance.getWeatherIcon(icon).enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//                    val imageUrl = response.raw().request().url().toString()
//                    liveData.postValue(imageUrl)
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Log.e("RetrofitError", "Retrofit onFailure on Icon: ${t.message}")
//                liveData.postValue(null)
//            }
//
//
//        })
//    }

    fun getForecastWeather(location: String,liveData: MutableLiveData<WeatherForecast>){
        retrofitServiceInstance.getFiveDayWeather(location).enqueue(object :Callback<WeatherForecast>{
            override fun onResponse(
                call: Call<WeatherForecast>,
                response: Response<WeatherForecast>
            ) {
               liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Log.e("RetrofitError", "Forecast Retrofit onFailure : ${t.message}")

                liveData.postValue(null)
            }

        })
    }
}