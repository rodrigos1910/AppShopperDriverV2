package com.example.appshopperdriver.network.response

import com.example.appshopperdriver.data.entities.DriverDTO
import com.example.appshopperdriver.model.ride.AssessmentModel
import com.example.appshopperdriver.model.ride.DriverModel

data class RideOption(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: Review,
    val value: Double
)

fun RideOption.toDriverModel(duration: String, distance: Double): DriverModel {
    return DriverModel(
        id = this.id,
        name = this.name,
        description = this.description,
        vehicle = this.vehicle,
        assessment = AssessmentModel(
            rating = this.review.rating,
            comment = this.review.comment
        ),
        value = this.value,
        duration = duration,
        distance = distance
    )
}


fun RideOption.toDriverDTO(): DriverDTO {
    return DriverDTO(
    codigo = this.id.toString(),
    name = this.name
    )
}