package com.example.appshopperdriver.network.request

data class EstimateRequest(
    val customer_id: String,
    val origin: String,
    val destination: String
)

