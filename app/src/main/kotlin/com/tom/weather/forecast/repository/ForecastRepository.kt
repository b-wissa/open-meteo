package com.tom.weather.forecast.repository

import com.tom.weather.common.model.Forecast
import com.tom.weather.common.model.LatLngLocation
import com.tom.weather.forecast.api.ForecastApi

interface ForecastRepository {
    suspend fun getForecastByLocation(latLngLocation: LatLngLocation): Result<Forecast>
}

class DefaultForecastRepository(private val forecastApi: ForecastApi) : ForecastRepository {
    override suspend fun getForecastByLocation(latLngLocation: LatLngLocation): Result<Forecast> {
        return forecastApi.getForecast(latLngLocation = latLngLocation).map { apiForecastResponse ->
            ApiForecastMapper.mapForecastResponse(apiForecastResponse = apiForecastResponse)
        }
    }
}
