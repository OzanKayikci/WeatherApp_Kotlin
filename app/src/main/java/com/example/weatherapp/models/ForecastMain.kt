package com.example.weatherapp.models

data class ForecastMain(
    val sea_level:Int,
    val grnd_level:Int,
    val temp_kf:Double,
    override val temp: Double,
    override val feels_like: Double,
    override val temp_min: Double,
    override val temp_max: Double,
    override val pressure: Int,
    override val humidity: Int

):IMain
