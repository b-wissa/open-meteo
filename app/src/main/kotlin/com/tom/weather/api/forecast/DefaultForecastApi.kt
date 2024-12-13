package com.tom.weather.api.forecast

import com.tom.weather.api.ApiError
import com.tom.weather.api.ForecastApiResponse
import com.tom.weather.model.LatLngLocation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess

class DefaultForecastApi(
    private val httpClient: HttpClient,

    ) : ForecastApi {
    override suspend fun getForecast(
        latLngLocation: LatLngLocation,
        currentFields: List<ForecastApi.CurrentField>,
        dailyFields: List<ForecastApi.DailyField>
    ): Result<ForecastApiResponse> = runCatching {
        val response = httpClient.get {
            method = HttpMethod.Get
            url {
                appendPathSegments(ForecastApi.FORECAST_END_POINT)
                parameters.append(
                    ForecastApi.QUERY_LATITUDE,
                    latLngLocation.latitude.toString()
                )
                parameters.append(
                    ForecastApi.QUERY_LONGITUDE,
                    latLngLocation.longitude.toString()
                )
                parameters.append(
                    ForecastApi.QUERY_CURRENT,
                    currentFields.joinToString(separator = ",", transform = { it.apiValue })
                )
                parameters.append(
                    ForecastApi.QUERY_DAILY,
                    dailyFields.joinToString(separator = ",", transform = { it.apiValue })
                )
            }

        }
        if (response.status.isSuccess()) {
            response.body<ForecastApiResponse>()
        } else {
            throw (response.body<ApiError>())
        }
    }
}
