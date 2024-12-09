package com.example.appshopperdriver.network.response

import com.example.appshopperdriver.network.request.Driver

data class Ride(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)
