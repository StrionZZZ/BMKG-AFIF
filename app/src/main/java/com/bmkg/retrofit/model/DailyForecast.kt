package com.bmkg.retrofit.model

data class DailyForecast(
    val dateLabel: String,
    val maxTemp: Int,
    val minTemp: Int,
    val description: String,
    val iconUrl: String,
    val rainChance: Int
)
