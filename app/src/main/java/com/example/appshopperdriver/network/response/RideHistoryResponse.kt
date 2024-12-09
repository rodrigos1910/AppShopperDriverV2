package com.example.appshopperdriver.network.response

import com.example.appshopperdriver.model.ride.AssessmentModel
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.model.ride.RideModel

data class RideHistoryResponse(
    val customer_id: String,
    val rides: List<Ride>
)

fun RideHistoryResponse.toRideModels(): List<RideModel> {
    return rides.map { ride ->
        RideModel(
            customer_id = this.customer_id,
            origin = ride.origin,
            destination = ride.destination,
            driver = DriverModel(
                id = ride.driver.id,
                name = ride.driver.name,
                description = "",
                vehicle = "",
                assessment = AssessmentModel(0.0, ""),
                value = ride.value,
                selecionado = false,
                distance = ride.distance,
                duration = ride.duration,
                date = ride.date
            )
        )
    }
}
