package com.tom.weather.api

import com.tom.weather.api.forecast.DefaultForecastApi
import com.tom.weather.api.forecast.ForecastApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val apiModule = module {
    factoryOf(::DefaultForecastApi) { bind<ForecastApi>() }
}

@Serializable
data class ForecastApiResponse(
    @SerialName("elevation") val elevation: Double,
)
