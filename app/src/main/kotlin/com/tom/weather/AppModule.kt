package com.tom.weather

import com.tom.weather.forecast.api.forecastApiModule
import com.tom.weather.forecast.forecastModule
import com.tom.weather.location.locationModule
import com.tom.weather.network.networkModule
import com.tom.weather.util.OffsetDateTimeSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.koin.dsl.module

val appModule = module {
    single {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            explicitNulls = false
            serializersModule = SerializersModule {
                contextual(OffsetDateTimeSerializer)

            }
        }
    }
    includes(
        networkModule,
        locationModule,
        forecastModule
    )
}
