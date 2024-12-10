package com.example.appshopperdriver.singleton

import android.util.Log
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.model.ride.RideModel

class SingletonRide {

    companion object{

        private lateinit var INSTANCE: RideModel

        fun  getRideInstance(): RideModel {

            if (!::INSTANCE.isInitialized){
                clearInstance()
            }

            return INSTANCE

        }

        fun createRide(ride: RideModel) {

            val gson = com.google.gson.Gson()
            val jsonRequest = gson.toJson(ride)
            Log.d("RideRepository", "Singleton Criado: $jsonRequest")

            INSTANCE = ride
        }

        fun clearInstance(){
            Log.d("RideRepository", "Singleton apagado")

            INSTANCE = RideModel("","","", null)
            //init()
        }

        fun createDriver(driver: DriverModel) {
            INSTANCE.driver = driver

        }

        fun removeDriver() {
            INSTANCE.driver = null

        }


        fun init(){
            clearInstance()
            INSTANCE =  RideModel("CT01","Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031","Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200", null )
        }
    }
}