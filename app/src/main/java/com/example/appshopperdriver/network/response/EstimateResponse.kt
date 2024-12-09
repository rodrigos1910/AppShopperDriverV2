package com.example.appshopperdriver.network.response

import com.example.appshopperdriver.data.entities.DriverDTO

data class EstimateResponse(
    val origin: Coordinate,
    val destination: Coordinate,
    val distance: Double,
    val duration: String,
    val options: List<RideOption>,
    val routeResponse: RouteResponse
)
fun EstimateResponse.toReadableString(): String {
    return """
        Origin: (${origin.latitude}, ${origin.longitude})
        Destination: (${destination.latitude}, ${destination.longitude})
        Distance: ${distance} km
        Duration: $duration
        Options:
        ${options.joinToString("\n") { "  - ${it.name}: ${it.value}" }}
        Route Response: ${routeResponse.toString()}
    """.trimIndent()
}

fun EstimateResponse.toDriverDTOList(): List<DriverDTO> {
    return this.options.map { it.toDriverDTO() }
}