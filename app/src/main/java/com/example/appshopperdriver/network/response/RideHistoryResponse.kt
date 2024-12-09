package com.example.appshopperdriver.network.response

data class RideHistoryResponse(
    val customer_id: String,
    val rides: List<Ride>
)
