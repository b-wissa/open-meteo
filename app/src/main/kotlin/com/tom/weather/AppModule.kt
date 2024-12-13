package com.tom.weather

import com.tom.weather.api.apiModule
import com.tom.weather.location.locationModule
import com.tom.weather.network.networkModule
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single {
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }
    includes(
        networkModule,
        locationModule,
        apiModule,
    )
}
