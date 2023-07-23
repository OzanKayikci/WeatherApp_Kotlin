package com.example.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.di.retrofit.RetrofitRepository
import com.example.weatherapp.models.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {
    var currentWeatherInfo: MutableLiveData<WeatherInfo>
    var weatherIcon : MutableLiveData<String>

    init {
        currentWeatherInfo = MutableLiveData()
        weatherIcon = MutableLiveData()
    }

    fun getObserverWeatherInfo(): MutableLiveData<WeatherInfo> {
        return currentWeatherInfo
    }

    fun getObservableWeatherIcon():MutableLiveData<String>{
        return  weatherIcon
    }

    fun loadWeatherInfo(location: String) {
        repository.getCurrentWeather(location, currentWeatherInfo)
    }

    fun loadWeatherIcon(icon:String){
        repository.getWeatherIcon(icon,weatherIcon)
    }
}