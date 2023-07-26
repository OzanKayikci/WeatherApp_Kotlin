package com.example.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.di.retrofit.RetrofitRepository
import com.example.weatherapp.models.WeatherForecast
import com.example.weatherapp.models.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {
    var currentWeatherInfo: MutableLiveData<WeatherInfo>
    var weatherIcon : MutableLiveData<String>

    var weatherForecast: MutableLiveData<WeatherForecast>

    init {
        currentWeatherInfo = MutableLiveData()
        weatherIcon = MutableLiveData()
        weatherForecast = MutableLiveData()
    }

    fun getObserverWeatherInfo(): MutableLiveData<WeatherInfo> {
        return currentWeatherInfo
    }

  fun getObserverForecastWeather():MutableLiveData<WeatherForecast>{
      return weatherForecast
  }
    fun loadWeatherInfo(location: String) {
        repository.getCurrentWeather(location, currentWeatherInfo)
    }

//    fun getObservableWeatherIcon():MutableLiveData<String>{
//        return  weatherIcon
//    }
//
//    fun loadWeatherIcon(icon:String){
//        repository.getWeatherIcon(icon,weatherIcon)
//    }

    fun loadForecastWeather(location: String){
        repository.getForecastWeather(location,weatherForecast)
    }
}