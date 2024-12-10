package com.example.appshopperdriver.service.repository

import android.content.Context
import android.util.Log
import com.example.appshopperdriver.R
import com.example.appshopperdriver.data.entities.DriverDTO
import com.example.appshopperdriver.network.RideApi
import com.example.appshopperdriver.network.request.ConfirmRequest
import com.example.appshopperdriver.network.request.EstimateRequest
import com.example.appshopperdriver.network.response.ConfirmResponse
import com.example.appshopperdriver.network.response.EstimateResponse
import com.example.appshopperdriver.network.response.RideHistoryResponse
import com.example.appshopperdriver.network.response.toDriverDTOList
import com.example.appshopperdriver.service.repository.local.ShopperDriverDataBase
import com.example.appshopperdriver.service.repository.remote.RetrofitClient
import com.example.appshopperdriver.util.ApiListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideRepository(context: Context)  : BaseRepository(context) {

    //retofit
    private val remote = RetrofitClient.getService(context, RideApi::class.java)

    //database
    private val empresaDao = ShopperDriverDataBase.getDataBase(context.applicationContext).DriverDAO()

    // Estimar uma corrida
    fun estimateRide(request: EstimateRequest, listener: ApiListener<EstimateResponse>) {
        if (!isConnerctionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        // Log do JSON enviado para a API
        val gson = com.google.gson.Gson()
        val jsonRequest = gson.toJson(request)
        Log.d("RideRepository", "JSON enviado para a API (EstimateRide): $jsonRequest")

        val call = remote.estimateRide(request)
        call.enqueue(object : Callback<EstimateResponse> {
            override fun onResponse(call: Call<EstimateResponse>, response: Response<EstimateResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        empresaDao.saveAll(it.toDriverDTOList())
                        listener.onSucess(it)
                    } ?: run {
                        listener.onFailure(context.getString(R.string.ERROR_ESTIMATE_RIDE))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    listener.onFailure(errorMessage ?: context.getString(R.string.ERROR_ESTIMATE_RIDE))
                }
            }

            override fun onFailure(call: Call<EstimateResponse>, t: Throwable) {
                Log.e("EstimateRide", "Falha na API: ${t.message}", t)
                listener.onFailure(context.getString(R.string.ERROR_ESTIMATE_RIDE))
            }
        })
    }

    // Confirmar uma corrida
    fun confirmRide(request: ConfirmRequest, listener: ApiListener<ConfirmResponse>) {
        if (!isConnerctionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        // Log do JSON enviado para a API
        val gson = com.google.gson.Gson()
        val jsonRequest = gson.toJson(request)
        Log.d("RideRepository", "JSON enviado para a API (confirmRide): $jsonRequest")

        val call = remote.confirmRide(request)
        call.enqueue(object : Callback<ConfirmResponse> {
            override fun onResponse(call: Call<ConfirmResponse>, response: Response<ConfirmResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSucess(it)
                    } ?: run {
                        listener.onFailure(context.getString(R.string.ERROR_CONFIRM_RIDE))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    listener.onFailure(errorMessage ?: context.getString(R.string.ERROR_CONFIRM_RIDE))
                }
            }

            override fun onFailure(call: Call<ConfirmResponse>, t: Throwable) {

                listener.onFailure(context.getString(R.string.ERROR_CONFIRM_RIDE))
            }
        })
    }

    // Histórico de corridas
    fun getRideHistory(customerId: String,driverid: String, listener: ApiListener<RideHistoryResponse>) {
        if (!isConnerctionAvailable()) {
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        // Log do JSON enviado para a API
        Log.d("RideRepository", "Dados enviado para a API (getRideHistory) customerId: $customerId driverid: $driverid ")

        val call = remote.getRideHistory(customerId,driverid)
        call.enqueue(object : Callback<RideHistoryResponse> {
            override fun onResponse(call: Call<RideHistoryResponse>, response: Response<RideHistoryResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listener.onSucess(it)
                    } ?: run {
                        listener.onFailure(context.getString(R.string.ERROR_NO_RIDES_FOUND))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    listener.onFailure(errorMessage ?: context.getString(R.string.ERROR_RIDE_HISTORY))
                }
            }

            override fun onFailure(call: Call<RideHistoryResponse>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_RIDE_HISTORY))
            }
        })
    }

    // Lista Motoristas
    fun getDriversFromLocal(): List<DriverDTO> {
        return empresaDao.list()
    }


    private fun parseErrorMessage(errorBody: String?): String? {
        return try {
            val json = JSONObject(errorBody?:"")
            json.getString("error_description")
        } catch (e: Exception) {
            null
        }
    }
}