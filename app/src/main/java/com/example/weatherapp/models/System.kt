package com.example.weatherapp.models

data class System  (
    val type: Int,
    val id: Int,
    override val country: String,
    override val sunset: Long,
    override val sunrise: Long
    ):ISunInfo