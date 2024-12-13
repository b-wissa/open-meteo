package com.tom.weather.forecast.api.forecast.model


import com.tom.weather.util.OffsetDateTimeSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class Current(
    @SerialName("apparent_temperature")
    val apparentTemperature: Double,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("precipitation")
    val precipitation: Double,
    @SerialName("rain")
    val rain: Double,
    @SerialName("showers")
    val showers: Double,
    @SerialName("snowfall")
    val snowfall: Double,
    @SerialName("temperature_2m")
    val temperature2m: Double,
    //@Serializable(with = OffsetDateTimeSerializer::class)
    @Contextual
    @SerialName("time")
    val time: OffsetDateTime,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double,
)
