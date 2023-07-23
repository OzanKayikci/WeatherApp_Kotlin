package com.example.weatherapp.models

data class System(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise:Long,
    val sunset:Long
    )