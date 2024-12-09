package com.example.appshopperdriver.util

interface ApiListener<T> {


    fun onSucess(result:T)

    fun onFailure(message: String)


}