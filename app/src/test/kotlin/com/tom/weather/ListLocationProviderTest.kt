package com.tom.weather

import com.tom.weather.location.ListLocationProvider
import com.tom.weather.model.IndexedLatLngLocation
import com.tom.weather.model.LatLngLocation
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ListLocationProviderTest {

    @Test
    fun `cycles locations when requested more times than list size`() {
        val location1 = LatLngLocation(latitude = 43.5, longitude = 16.788)
        val location2 = LatLngLocation(latitude = 53.2, longitude = 13.213)
        val location3 = LatLngLocation(latitude = 63.5632, longitude = 15.712)
        val locationProvider = ListLocationProvider(listOf(location1, location2, location3))
        val actualLocations = mutableListOf<IndexedLatLngLocation>()
        for (i in 0..9) {
            actualLocations.add(locationProvider.getNextLocation())
        }
        assertEquals(
            listOf(
                0 to location1,
                1 to location2,
                2 to location3,
                0 to location1,
                1 to location2,
                2 to location3,
                0 to location1,
                1 to location2,
                2 to location3,
                0 to location1,
            ), actualLocations
        )
    }
}
