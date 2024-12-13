package com.tom.weather.forecast.api.forecast.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: String?,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: String?,
    @SerialName("temperature_2m_max")
    val temperature2mMax: String?,
    @SerialName("temperature_2m_min")
    val temperature2mMin: String?,
)
