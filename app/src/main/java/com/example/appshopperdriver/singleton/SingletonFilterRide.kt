package com.example.appshopperdriver.singleton

import com.example.appshopperdriver.model.orders.FilterRideModel
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.model.ride.RideModel

class SingletonFilterRide {
    companion object{

        private lateinit var INSTANCE: FilterRideModel

        fun  getInstance(): FilterRideModel {

            if (!::INSTANCE.isInitialized){
                clearInstance()
            }

            return INSTANCE

        }

        fun create(ride: FilterRideModel) {
            INSTANCE = ride
        }

        fun clearInstance(){

            INSTANCE = FilterRideModel("","")
            //init()
        }


        fun init(){

            INSTANCE =  FilterRideModel("CT01","1")
        }
    }
}