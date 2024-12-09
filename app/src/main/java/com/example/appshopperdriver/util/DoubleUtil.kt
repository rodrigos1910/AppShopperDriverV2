package com.example.appshopperdriver.util

object DoubleUtil {

    fun doubleToUiMoney(valor: Double): String {
        return "R$:" + String.format("%.2f", valor).replace(".", ",")
    }

    fun formatToTwoDecimal(value: Double?): String {
        return if (value != null) String.format("%.2f", value) else "0.00"
    }


}