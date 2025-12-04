package com.bmkg.retrofit.model

data class CuacaItem(
    val datetime: String? = null,
    val t: Int? = null,            // temperature
    val tp: Double? = null,        // precipitation
    val weather_desc: String? = null,
    val weather_desc_en: String? = null,
    val image: String? = null,
    val local_datetime: String? = null
) {
    fun getHour(): String {
        val ld = local_datetime ?: return ""
        return if (ld.length >= 16) ld.substring(11, 16) else ""
    }
}
