package com.example.weatherapp.models

data class CityToForecast(
    val population: Int,
    override val timezone: Int,
    override val id: Int,
    override val name: String,
    override val coordinates: Coordinates,
    override val sunrise: Long,
    override val sunset: Long,
    override val country: String

) : ICityInfo, ISunInfo