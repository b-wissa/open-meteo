package com.tom.weather.latest.usecase

import com.tom.weather.common.model.Forecast
import com.tom.weather.common.model.IndexedLatLngLocation
import com.tom.weather.forecast.repository.ForecastRepository
import com.tom.weather.latest.usecase.LatestWeather.WeatherState
import com.tom.weather.location.UserLocationProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class GetLatestWeatherUseCase(
    private val userLocationProvider: UserLocationProvider,
    private val forecastRepository: ForecastRepository,
) {
    operator fun invoke(): Flow<LatestWeather> = callbackFlow {
        var fetchForecastJob: Job = Job()
        userLocationProvider().collect { indexedLatLngLocation ->
            fetchForecastJob.cancel()
            send(
                LatestWeather(
                    indexedLatLngLocation = indexedLatLngLocation,
                    weatherState = WeatherState.Loading,
                )
            )

            fetchForecastJob = launch {
                forecastRepository.getForecastByLocation(latLngLocation = indexedLatLngLocation.second)
                    .onSuccess { forecast ->
                        send(
                            LatestWeather(
                                indexedLatLngLocation = indexedLatLngLocation,
                                weatherState = WeatherState.Ready(forecast),
                            )
                        )
                    }.onFailure {
                        LatestWeather(
                            indexedLatLngLocation = indexedLatLngLocation,
                            weatherState = WeatherState.Error,
                        )
                    }
            }
        }
        awaitClose {
            fetchForecastJob.cancel()
        }
    }
}

data class LatestWeather(
    val indexedLatLngLocation: IndexedLatLngLocation,
    val weatherState: WeatherState,
) {
    sealed interface WeatherState {
        data object Loading : WeatherState
        data class Ready(val forecast: Forecast) : WeatherState
        data object Error : WeatherState
    }
}
