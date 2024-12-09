package com.example.appshopperdriver.model.ride

import com.example.appshopperdriver.network.request.ConfirmRequest
import com.example.appshopperdriver.network.request.Driver
import com.example.appshopperdriver.network.request.EstimateRequest
import com.example.appshopperdriver.network.response.toDriverModel

data class RideModel(
    val customer_id: String,
    val origin: String,
    val destination: String,
    var driver : DriverModel?
)

fun RideModel.toEstimateRequest(): EstimateRequest {
    return EstimateRequest(
        customer_id = this.customer_id,
        origin = this.origin,
        destination = this.destination
    )
}

fun RideModel.toConfirmRequest( ): ConfirmRequest {
    return ConfirmRequest(
        customer_id = this.customer_id, // Supondo que `RideModel` tenha um campo `customerId`
        origin = this.origin,
        destination = this.destination,
        distance = this.driver?.distance ?: 0.0,
        duration = this.driver?.duration ?: "",
        driver =   com.example.appshopperdriver.network.request.Driver(
            id = this.driver?.id ?: 0,
            name = this?.driver?.name ?: ""
        ),
        value = this.driver?.value ?: 0.0,
    )
}