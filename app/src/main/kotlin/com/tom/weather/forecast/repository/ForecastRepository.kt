package com.tom.weather.forecast.repository

import com.tom.weather.common.model.LatLngLocation
import java.time.OffsetDateTime
import java.util.Date

interface ForecastRepository {
    suspend fun getForecastByLocation(latLngLocation: LatLngLocation): Result<Forecast>
}

data class Forecast(
    val currentForecast: CurrentForecast,
    val upcomingDays: List<DailyForecast>,
)

data class CurrentForecast(
    val isDay: Boolean,
    val precipitation: Int,
    val rain: WeatherUnit,
    val realFeel: WeatherUnit,
    val showers: WeatherUnit,
    val snowFall: WeatherUnit,
    val temperature: WeatherUnit,
    val time: OffsetDateTime,
    val weatherCode: WeatherCode?,
    val windSpeed: WeatherUnit,
) {

}

data class DailyForecast(
    val date: OffsetDateTime,
    val maxRealFeel: WeatherUnit,
    val maxTemperature: WeatherUnit,
    val minRealFeel: WeatherUnit,
    val minTemperature: WeatherUnit,
    val sunrise: OffsetDateTime,
    val sunset: OffsetDateTime,
    val weatherCode: WeatherCode?,
)

data class WeatherUnit(val value: Double, val unit: String)
enum class WeatherCode(val value: Int) {
    CLEAR_SKY(0),
    MAINLY_CLEAR(1),
    PARTLY_CLOUDY(2),
    OVERCAST(3),
    FOG(45),
    DEPOSITING_RIME_FOG(48),
    DRIZZLE_LIGHT(51),
    DRIZZLE_MODERATE(53),
    DRIZZLE_DENSE_INTENSITY(55),
    FREEZING_DRIZZLE_LIGHT(56),
    FREEZING_DRIZZLE_DENSE(57),
    RAIN_SLIGHT(61),
    RAIL_MODERATE(63),
    RAIN_HEAVY(65),
    FREEZING_RAIN_LIGHT(66),
    FREEZING_RAIN_HEAVY(67),
    SNOW_FALL_SLIGHT(71),
    SNOW_FALL_MODERATE(73),
    SNOW_FALL_HEAVY(75),
    SNOW_GRAINS(77),
    RAIN_SHOWERS_SLIGHT(80),
    RAIN_SHOWERS_MODERATE(81),
    RAIN_SHOWERS_VIOLENT(82),
    SNOW_SHOWERS_SLIGHT(86),
    SNOW_SHOWERS_HEAVY(86),
    THUNDERSTORM_SLIGHT_OR_MODERATE(95),
    THUNDERSTORM_WITH_SLIGHT_HAIL(96),
    THUNDERSTORM_WITH_HEAVY_HAIL(99),

}
