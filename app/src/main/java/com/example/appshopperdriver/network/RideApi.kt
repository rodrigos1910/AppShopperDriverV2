package com.example.appshopperdriver.network

import com.example.appshopperdriver.network.request.ConfirmRequest
import com.example.appshopperdriver.network.request.EstimateRequest
import com.example.appshopperdriver.network.response.ConfirmResponse
import com.example.appshopperdriver.network.response.EstimateResponse
import com.example.appshopperdriver.network.response.RideHistoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RideApi {

    @POST("ride/estimate")
    fun estimateRide(
        @Body estimateRequest: EstimateRequest
    ): Call<EstimateResponse>

    @PATCH("ride/confirm")
    fun confirmRide(
        @Body confirmRequest: ConfirmRequest
    ): Call<ConfirmResponse>

    @GET("ride/{customer_id}")
    fun getRideHistory(
        @Path("customer_id") customerId: String,
        @Query("driver_id") driverId: String? = null
    ): Call<RideHistoryResponse>
}