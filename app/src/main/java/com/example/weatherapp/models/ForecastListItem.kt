package com.example.weatherapp.models

data class ForecastListItem(

    override val dt: Long,
    override val weather: List<Weather>,
    override val clouds: Clouds,
    override val wind: Wind,
    override val visibility: Int,
    val dt_txt:String,
    val pop:Double,
    val main: ForecastMain

):IWeatherBase
