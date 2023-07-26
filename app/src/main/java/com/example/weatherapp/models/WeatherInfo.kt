package com.example.weatherapp.models

data class WeatherInfo(

    val base:String,
    val main:WeatherMain,
    val sys:System,
    val cod:Int,
    override val timezone: Int,
    override val id: Int,
    override val name: String,
    override val coordinates: Coordinates,
    override val dt: Long,
    override val weather: List<Weather>,
    override val clouds: Clouds,
    override val wind: Wind,
    override val visibility: Int,

    ):ICityInfo,IWeatherBase
