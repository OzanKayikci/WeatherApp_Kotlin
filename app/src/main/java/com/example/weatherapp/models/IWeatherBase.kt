package com.example.weatherapp.models

interface IWeatherBase {
    val dt:Long
    val weather:List<Weather>
    val clouds: Clouds
    val wind:Wind
    val visibility:Int

}