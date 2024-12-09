package com.example.appshopperdriver.ui.ride

import com.example.appshopperdriver.model.ride.DriverModel

interface RideDriverListener {

    fun onSelectItem(driver: DriverModel, remove: Boolean){

    }
}