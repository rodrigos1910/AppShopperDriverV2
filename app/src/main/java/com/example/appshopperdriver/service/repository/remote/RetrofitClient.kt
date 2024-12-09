package com.example.appshopperdriver.service.repository.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient private constructor() {

    companion object{


        private lateinit var INSTANCE : Retrofit


        private  var urlApi : String = "https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws"

        private fun getRetrofitInstance(context: Context) : Retrofit {


            val httpClient = OkHttpClient.Builder()


            //configura o timeout
            httpClient.connectTimeout(180, TimeUnit.SECONDS)
            httpClient.readTimeout(180, TimeUnit.SECONDS)
            httpClient.writeTimeout(180, TimeUnit.SECONDS)

            if (!::INSTANCE.isInitialized){

                synchronized(RetrofitClient::class) { // evita problemas de intanciar ao mesmo tempo(mesma thead) no singleton
                    INSTANCE = Retrofit.Builder()
                        .baseUrl(urlApi)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }

            }


            return INSTANCE
        }
        /// retorno generico do retrofit, serve para qualquer classo usando o T
        fun <T> getService(context: Context, serviceClass: Class<T>): T{
            return getRetrofitInstance(context).create(serviceClass)
        }




    }




}