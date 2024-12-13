package com.tom.weather.forecast.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val forecastApiModule = module {
    factoryOf(::DefaultForecastApi) { bind<ForecastApi>() }
}

@Serializable
data class ForecastApiResponse(
    @SerialName("elevation") val elevation: Double,
)
