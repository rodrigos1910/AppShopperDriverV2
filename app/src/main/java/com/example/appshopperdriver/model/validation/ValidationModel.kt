package com.example.appshopperdriver.model.validation

class ValidationModel(message : String = "") {

    private var status: Boolean = true
    private var validationMessage: String = ""

    init {
        if (message != ""){
            validationMessage = message
            status = false
        }

    }

    fun status() = status
    fun message() = validationMessage
}