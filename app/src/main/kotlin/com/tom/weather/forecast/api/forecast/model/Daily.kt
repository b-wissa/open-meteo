package com.tom.weather.forecast.api.forecast.model


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Daily(
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double>,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double>,
    @SerialName("sunrise")
    @Contextual
    val sunrise: List<Long>,
    @SerialName("sunset")
    val sunset: List<Long>,
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerialName("time")
    val time: List<Long>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)
