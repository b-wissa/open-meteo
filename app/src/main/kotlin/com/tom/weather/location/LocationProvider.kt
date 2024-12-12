package com.tom.weather.location

import com.tom.weather.model.IndexedLatLngLocation

internal interface LocationProvider {
    fun getNextLocation(): IndexedLatLngLocation
}
