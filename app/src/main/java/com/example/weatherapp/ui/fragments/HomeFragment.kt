package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.models.WeatherInfo
import com.example.weatherapp.viewModel.HomePageViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment:Fragment() {
    private  var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private  val viewModal by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory)[HomePageViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        fetchWeatherInfo()
        observableFunctions()
        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

  private  fun fetchWeatherInfo(){
        CoroutineScope(Dispatchers.IO).launch {
           val job1: Deferred<Unit> = async {
               viewModal.loadWeatherInfo("aydin,tr")
           }
            job1.await()
        }
    }

    private fun observableFunctions(){
        viewModal.getObserverWeatherInfo().observe(viewLifecycleOwner){t->

            if (t!=null){

                setResultsToView(t)
            }
        }



    }

    private fun setResultsToView(weatherInfo: WeatherInfo){
        binding.weatherType.text = weatherInfo.weather[0].main

        binding.weatherTemp.text = "${weatherInfo.main.temp}°"
        binding.weatherTempHeight.text = "H:${weatherInfo.main.temp_max}  "
        binding.weatherTempLow.text = "L:${weatherInfo.main.temp_min}"
        binding.humidityText.text = "${weatherInfo.main.humidity}%"
        binding.wwindText.text = "${weatherInfo.wind.speed} km/h"
        binding.cloudText.text = "${weatherInfo.clouds.all}%"
        binding.pressureText.text = ((weatherInfo.main.pressure/1000).toDouble()).toString()
        binding.visibilityText.text = "${((weatherInfo.visibility/1000).toDouble())} km"
        binding.feelsText.text = "${weatherInfo.main.feels_like}°"

        val weatherIcon= binding.weatherIcon
        Picasso.get().load("https://openweathermap.org/img/wn/${weatherInfo.weather[0].icon}@4x.png").into(weatherIcon)
    }


}