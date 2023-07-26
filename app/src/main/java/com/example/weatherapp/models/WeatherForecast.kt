package com.example.weatherapp.models

data class WeatherForecast(
val code:String,
val message:String,
val cnt:Int,
val city:CityToForecast,
val list: List<ForecastListItem>
)
