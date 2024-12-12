package com.tom.weather.location

import com.tom.weather.model.IndexedLatLngLocation
import com.tom.weather.model.LatLngLocation

internal class ListLocationProvider(
    private val locations: List<LatLngLocation> = defaultLocations,
) : LocationProvider {
    private var currentLocationIndex = 0

    init {
        if (locations.isEmpty()) {
            error("Locations list should not be empty")
        }
    }

    override fun getNextLocation(): IndexedLatLngLocation {
        if (currentLocationIndex >= locations.size) {
            currentLocationIndex = 0
        }
        return currentLocationIndex to locations[currentLocationIndex].also {
            currentLocationIndex++
        }
    }

    companion object {
        internal val defaultLocations = listOf(
            LatLngLocation(latitude = 53.619653, longitude = 10.079969),
            LatLngLocation(latitude = 53.080917, longitude = 8.847533),
            LatLngLocation(latitude = 52.378385, longitude = 9.794862),
            LatLngLocation(latitude = 52.496385, longitude = 13.444041),
            LatLngLocation(latitude = 53.866865, longitude = 10.739542),
            LatLngLocation(latitude = 54.304540, longitude = 10.152741),
            LatLngLocation(latitude = 54.797277, longitude = 9.491039),
            LatLngLocation(latitude = 52.426412, longitude = 10.821392),
            LatLngLocation(latitude = 53.542788, longitude = 8.613462),
            LatLngLocation(latitude = 53.141598, longitude = 8.242565)
        )
    }
}
