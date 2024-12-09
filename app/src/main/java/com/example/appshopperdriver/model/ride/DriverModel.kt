package com.example.appshopperdriver.model.ride

data class DriverModel (
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val assessment: AssessmentModel,
    val value: Double,
    val selecionado: Boolean = false,
    val distance: Double,
    val duration: String

)
