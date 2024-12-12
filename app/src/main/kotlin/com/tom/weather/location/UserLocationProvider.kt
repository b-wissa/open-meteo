package com.tom.weather.location

import com.tom.weather.model.IndexedLatLngLocation
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface UserLocationProvider {
    operator fun invoke(): Flow<IndexedLatLngLocation>
}

internal class DefaultUserLocationProvider(
    private val locationProvider: LocationProvider,
    private val interval: Duration,
) : UserLocationProvider {
    override fun invoke(): Flow<IndexedLatLngLocation> = flow {
        while (currentCoroutineContext().isActive) {
            emit(locationProvider.getNextLocation())
            delay(interval)
        }
    }

    companion object {
        internal val defaultLocationUpdateInterval = 10.seconds
    }
}
