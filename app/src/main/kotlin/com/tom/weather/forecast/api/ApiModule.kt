package com.tom.weather.forecast.api

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val forecastApiModule = module {
    factoryOf(::DefaultForecastApi) { bind<ForecastApi>() }
}
