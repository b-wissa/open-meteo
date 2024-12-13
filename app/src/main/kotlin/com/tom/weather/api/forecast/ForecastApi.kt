package com.tom.weather.api.forecast

import com.tom.weather.api.ForecastApiResponse
import com.tom.weather.model.LatLngLocation

interface ForecastApi {
    suspend fun getForecast(
        latLngLocation: LatLngLocation,
        currentFields: List<CurrentField> = CurrentField.entries,
        dailyFields: List<DailyField> = DailyField.entries,
    ): Result<ForecastApiResponse>

    enum class CurrentField(val apiValue: String) {
        TEMPERATURE("temperature_2m"),
        REAL_FEEL("apparent_temperature"),
        PRECIPITATION("precipitation"),
        RAIN("rain"),
        SHOWERS("showers"),
        SNOWFALL("snowfall"),
        WEATHER_CODE("weather_code"),
        WIND_SPEED("wind_speed_10m"),
    }

    enum class DailyField(val apiValue: String) {
        TEMP_MAX("temperature_2m_max"),
        TEMP_MIN("temperature_2m_min"),
        REAL_FEEL_MAX("apparent_temperature_max"),
        REAL_FEEL_MIN("apparent_temperature_min"),
        SUNRISE("sunrise"),
        SUNSET("sunset"),
    }

    companion object {
        internal const val FORECAST_END_POINT = "forecast"
        internal const val QUERY_LATITUDE = "latitude"
        internal const val QUERY_LONGITUDE = "longitude"
        internal const val QUERY_CURRENT = "current"
        internal const val QUERY_DAILY = "daily"
    }
}
