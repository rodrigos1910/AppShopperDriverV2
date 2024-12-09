package com.example.appshopperdriver.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val outputDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    fun formatDateToBrazilian(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "Data Indisponível"
        return try {
            val date = inputDateFormat.parse(dateString)
            if (date != null) {
                outputDateFormat.format(date)
            } else {
                "Data Indisponível"
            }
        } catch (e: Exception) {
            "Data Indisponível"
        }
    }
}