package com.example.appshopperdriver.ui.orders

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appshopperdriver.R
import com.example.appshopperdriver.data.entities.DriverDTO
import com.example.appshopperdriver.model.orders.FilterRideModel
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.service.constants.ShopperContants
import com.example.appshopperdriver.service.repository.local.ShopperDriverDataBase
import com.example.appshopperdriver.singleton.SingletonFilterRide
import com.example.appshopperdriver.singleton.SingletonRide

class OrdersViewModel(application: Application) : AndroidViewModel(application) {

    private val  myContext = application.applicationContext

    private val _invalidField = MutableLiveData<HashMap<String,String>>()
    val invalidField: LiveData<HashMap<String, String>> = _invalidField


    private val _drivers = MutableLiveData<List<DriverDTO>>()
    val drivers: LiveData<List<DriverDTO>> = _drivers


    fun validateFilter(filter: FilterRideModel): Boolean{


        val fiels:HashMap<String,String> = HashMap<String,String>()

        if (filter.customer_id.isEmpty()) {
            fiels.put(ShopperContants.INPUT.ORDER.CUSTOMER_ID, myContext.getString(R.string.error_customer_id) )
        }

        if (filter.driver_id.isEmpty()) {
            fiels.put(ShopperContants.INPUT.ORDER.DRIVER_ID, myContext.getString(R.string.error_driver_id) )
        }

        if (fiels.size > 0) {
            _invalidField.value = fiels
            return false
        }else{
            _invalidField.value = HashMap()
            SingletonFilterRide.create(filter)
            return true
        }

    }




    fun loadDrivers() {
        // Aqui você deve buscar a lista de motoristas do banco de dados
        val driverList = ShopperDriverDataBase
            .getDataBase(myContext)
            .DriverDAO()
            .list()

        _drivers.value = driverList
    }


    fun getDriverIdByName(name: String): String? {
        val currentDrivers = drivers.value ?: return null
        return currentDrivers.find { it.name == name }?.codigo
    }



}