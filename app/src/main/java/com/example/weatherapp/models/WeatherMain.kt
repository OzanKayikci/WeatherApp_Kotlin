package com.example.weatherapp.models

data class WeatherMain(
    override val temp: Double,
    override val feels_like: Double,
    override val temp_min: Double,
    override val temp_max: Double,
    override val pressure: Int,
    override val humidity: Int

):IMain
