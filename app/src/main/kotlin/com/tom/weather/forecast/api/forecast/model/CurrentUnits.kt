package com.tom.weather.forecast.api.forecast.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnits(
    @SerialName("apparent_temperature")
    val apparentTemperature: String?,
    @SerialName("precipitation")
    val precipitation: String?,
    @SerialName("rain")
    val rain: String?,
    @SerialName("showers")
    val showers: String?,
    @SerialName("snowfall")
    val snowfall: String?,
    @SerialName("temperature_2m")
    val temperature2m: String?,
    @SerialName("wind_speed_10m")
    val windSpeed10m: String?
)
