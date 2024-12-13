package com.tom.weather.location

import com.tom.weather.common.model.IndexedLatLngLocation

internal interface LocationProvider {
    fun getNextLocation(): IndexedLatLngLocation
}
