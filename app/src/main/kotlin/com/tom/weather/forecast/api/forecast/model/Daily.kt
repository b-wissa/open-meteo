package com.tom.weather.forecast.api.forecast.model


import com.tom.weather.util.OffsetDateTimeListSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime


@Serializable
data class Daily(
    @SerialName("apparent_temperature_max")
    val apparentTemperatureMax: List<Double>,
    @SerialName("apparent_temperature_min")
    val apparentTemperatureMin: List<Double>,
    @Serializable(with = OffsetDateTimeListSerializer::class)
    @SerialName("sunrise")
    val sunrise: List<OffsetDateTime>,
    @Serializable(with = OffsetDateTimeListSerializer::class)
    @SerialName("sunset")
    val sunset: List<OffsetDateTime>,
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,

    @Serializable(with = OffsetDateTimeListSerializer::class)
    @SerialName("time")
    val time: List<OffsetDateTime>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)
