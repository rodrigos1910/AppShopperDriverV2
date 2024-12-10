package com.example.appshopperdriver.ui.ride

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appshopperdriver.R
import com.example.appshopperdriver.model.ride.DriverModel
import com.example.appshopperdriver.model.ride.RideModel
import com.example.appshopperdriver.model.ride.toConfirmRequest
import com.example.appshopperdriver.model.ride.toEstimateRequest
import com.example.appshopperdriver.model.validation.ValidationModel
import com.example.appshopperdriver.network.response.ConfirmResponse
import com.example.appshopperdriver.network.response.EstimateResponse
import com.example.appshopperdriver.network.response.toDriverModel
import com.example.appshopperdriver.network.response.toReadableString
import com.example.appshopperdriver.service.constants.ShopperContants
import com.example.appshopperdriver.service.repository.RideRepository
import com.example.appshopperdriver.singleton.SingletonRide
import com.example.appshopperdriver.util.ApiListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint

class RideViewModel(application: Application) :
    AndroidViewModel(application) {

    private val rideRepository: RideRepository = RideRepository(application.applicationContext)

    private val  myContext = application.applicationContext


    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    private val _currentLocation = MutableLiveData<GeoPoint?>()
    val currentLocation: LiveData<GeoPoint?> = _currentLocation

    private val _invalidField = MutableLiveData<HashMap<String,String>>()
    val invalidField: LiveData<HashMap<String, String>> = _invalidField

    private val _loadRide = MutableLiveData<RideModel>()
    val loadRide: LiveData<RideModel> = _loadRide

    private val _listDrivers = MutableLiveData<List<DriverModel>>()
    val listDrivers: LiveData<List<DriverModel>> = _listDrivers

    private val _loadDrivers = MutableLiveData<ValidationModel>()
    val loadDrivers: LiveData<ValidationModel> = _loadDrivers

    private val _listRoute = MutableLiveData<String>()
    val listRoute: LiveData<String> = _listRoute

    private val _finishRide = MutableLiveData<Boolean>()
    val finishRide: LiveData<Boolean> = _finishRide

    private val _confirmRide = MutableLiveData<ValidationModel>()
    val confirmRide: LiveData<ValidationModel> = _confirmRide


    fun loadCurrentLocation() {
        // Verifica se a permissão foi concedida
        if (ContextCompat.checkSelfPermission(
                getApplication(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Se a permissão foi concedida, obtém a última localização
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    _currentLocation.postValue(GeoPoint(location.latitude, location.longitude))
                } else {
                    _currentLocation.postValue(null) // Caso não encontre a localização
                }
            }
        } else {
            // Permissão não concedida, você pode disparar um evento ou logar uma mensagem
            _currentLocation.postValue(null)
        }
    }


    fun validateRide(ride: RideModel): Boolean{

        SingletonRide.createRide(ride)

        val fiels:HashMap<String,String> = HashMap<String,String>()

        if (ride.customer_id.isEmpty()) {
            fiels.put(ShopperContants.INPUT.RIDE.CUSTOMER_ID, myContext.getString(R.string.error_customer_id) )
        }

        if (ride.origin.isEmpty()) {
            fiels.put(ShopperContants.INPUT.RIDE.ORIGIN, myContext.getString(R.string.error_origin) )
        }

        if (ride.destination.isEmpty()) {
            fiels.put(ShopperContants.INPUT.RIDE.DESTINATION, myContext.getString(R.string.error_destination) )
        }

        if (fiels.size > 0) {
            _invalidField.value = fiels
            return false
        }else{

            _invalidField.value = HashMap()
            return true
        }

    }



    fun requestDrivers(ride: RideModel) {

        val listener = object : ApiListener<EstimateResponse> {
            override fun onSucess(result: EstimateResponse) {

                // Log do JSON enviado para a API
                val gson = com.google.gson.Gson()
                val jsonRequest = gson.toJson(result)
                Log.d("RideRepository", "JSON recebido para a API (requestDrivers): $jsonRequest")

                // Verificar se o retorno contém dados válidos
                if (result.options.isEmpty() || result.distance == 0.0 || result.duration == "0") {

                    _loadDrivers.value = ValidationModel(myContext.getString(R.string.ERROR_EMPLYT_DRIVER))
                    return
                }


                val drivers = mutableListOf<DriverModel>()

                result.options.forEach{
                    drivers.add(it.toDriverModel(result.duration,result.distance))
                }
                _listDrivers.value = drivers
                _loadDrivers.value = ValidationModel()

                _listRoute.value = result.routeResponse.routes[0].polyline.encodedPolyline

            }

            override fun onFailure(message: String) {
                _loadDrivers.value = ValidationModel(message)
            }

        }
        //busca as lojas com estoque
        rideRepository.estimateRide(ride.toEstimateRequest(),listener)

    }


    fun confirmRide() {

        val ride = SingletonRide.getRideInstance()

        val listener = object : ApiListener<ConfirmResponse> {
            override fun onSucess(result: ConfirmResponse) {

                // Log do JSON enviado para a API
                val gson = com.google.gson.Gson()
                val jsonRequest = gson.toJson(result)
                Log.d("RideRepository", "JSON recebido para a API (confirmRide): $jsonRequest")

                _finishRide.value = true
                _confirmRide.value = ValidationModel()
            }

            override fun onFailure(message: String) {
                _finishRide.value = false
                _confirmRide.value = ValidationModel(message)
            }

        }
        //busca as lojas com estoque
        rideRepository.confirmRide(ride.toConfirmRequest(),listener)

    }


    fun loadRide(){

        var ride = SingletonRide.getRideInstance()
        _loadRide.value = ride
    }

    fun setSelectedDriver(driver: DriverModel) {
        SingletonRide.createDriver(driver)
    }

    fun removeSelectedDriver( ) {
        SingletonRide.removeDriver()
    }


}