package com.tom.weather.forecast

import com.tom.weather.forecast.api.forecastApiModule
import com.tom.weather.forecast.repository.DefaultForecastRepository
import com.tom.weather.forecast.repository.ForecastRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val forecastModule = module {
    includes(forecastApiModule)
    factoryOf(::DefaultForecastRepository) { bind<ForecastRepository>() }
}
