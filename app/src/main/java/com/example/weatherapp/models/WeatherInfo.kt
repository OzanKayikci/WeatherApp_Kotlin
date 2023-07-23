package com.example.weatherapp.models

data class WeatherInfo(
val coordinates: Coordinates,
val weather:List<Weather>,
val base:String,
val main:WeatherMain,
val visibility:Int,
val wind:Wind,
val clouds: Clouds,
val dt:Long,
val sys:System,
val timezone:Int,
val id:Int,
val name:String,
val cod:Int,

)
