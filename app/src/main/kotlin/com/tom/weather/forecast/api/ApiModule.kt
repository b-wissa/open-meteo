package com.tom.weather.forecast.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val forecastApiModule = module {
    factoryOf(::DefaultForecastApi) { bind<ForecastApi>() }
}
