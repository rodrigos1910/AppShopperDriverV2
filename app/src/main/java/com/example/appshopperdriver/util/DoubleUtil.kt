package com.example.appshopperdriver.util

object DoubleUtil {

    fun doubleToUiMoney(valor: Double): String {
        return "R$:" + String.format("%.2f", valor).replace(".", ",")
    }

}