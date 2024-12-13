package com.tom.weather.forecast.api.forecast.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiForecastResponse(
    @SerialName("current")
    val current: Current,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    @SerialName("daily")
    val daily: Daily,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    @SerialName("elevation")
    val elevation: Double,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
)
