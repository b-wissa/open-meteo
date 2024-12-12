package com.tom.weather

import android.app.Application
import com.tom.weather.location.locationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(locationModule)
        }
    }
}
