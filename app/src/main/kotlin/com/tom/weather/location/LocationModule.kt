package com.tom.weather.location


import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    factory<LocationProvider> { ListLocationProvider(locations = ListLocationProvider.defaultLocations) }
    factory<UserLocationProvider> {
        DefaultUserLocationProvider(
            locationProvider = get(),
            interval = DefaultUserLocationProvider.defaultLocationUpdateInterval,
        )
    }
}
