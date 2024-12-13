package com.tom.weather.forecast.api

import com.tom.weather.common.model.LatLngLocation
import com.tom.weather.forecast.api.forecast.model.ApiForecastResponse

interface ForecastApi {
    suspend fun getForecast(
        latLngLocation: LatLngLocation,
        currentFields: List<CurrentField> = CurrentField.entries,
        dailyFields: List<DailyField> = DailyField.entries,
    ): Result<ApiForecastResponse>

    enum class CurrentField(val apiValue: String) {
        IS_DAY("is_day"),
        PRECIPITATION("precipitation"),
        RAIN("rain"),
        REAL_FEEL("apparent_temperature"),
        SHOWERS("showers"),
        SNOWFALL("snowfall"),
        TEMPERATURE("temperature_2m"),
        WEATHER_CODE("weather_code"),
        WIND_SPEED("wind_speed_10m"),
    }

    enum class DailyField(val apiValue: String) {
        REAL_FEEL_MAX("apparent_temperature_max"),
        REAL_FEEL_MIN("apparent_temperature_min"),
        SUNRISE("sunrise"),
        SUNSET("sunset"),
        TEMP_MAX("temperature_2m_max"),
        TEMP_MIN("temperature_2m_min"),
        WEATHER_CODE("weather_code"),
    }

    companion object {
        internal const val FORECAST_END_POINT = "forecast"
        internal const val QUERY_LATITUDE = "latitude"
        internal const val QUERY_LONGITUDE = "longitude"
        internal const val QUERY_CURRENT = "current"
        internal const val QUERY_DAILY = "daily"
        internal const val QUERY_TIME_FORMAT = "timeformat"
        internal const val UNIX_TIME_FORMAT = "unixtime"
    }
}
