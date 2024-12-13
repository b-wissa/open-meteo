package com.tom.weather.forecast.repository

import android.util.Log
import com.tom.weather.common.model.CurrentForecast
import com.tom.weather.common.model.DailyForecast
import com.tom.weather.common.model.Forecast
import com.tom.weather.common.model.WeatherCode
import com.tom.weather.common.model.WeatherUnit
import com.tom.weather.forecast.api.forecast.model.ApiForecastResponse
import com.tom.weather.forecast.api.forecast.model.Daily
import com.tom.weather.forecast.api.forecast.model.DailyUnits
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

object ApiForecastMapper {
    private val zoneId = ZoneId.of("UTC")
    fun mapForecastResponse(apiForecastResponse: ApiForecastResponse): Forecast {
        with(apiForecastResponse) {
            return Forecast(
                currentForecast = CurrentForecast(
                    isDay = current.isDay == 1,
                    precipitation = WeatherUnit(
                        value = current.precipitation,
                        unit = currentUnits.precipitation.orEmpty()
                    ),
                    rain = WeatherUnit(
                        value = current.rain,
                        unit = currentUnits.rain.orEmpty()
                    ),
                    realFeel = WeatherUnit(
                        value = current.apparentTemperature,
                        unit = currentUnits.apparentTemperature.orEmpty()
                    ),
                    showers = WeatherUnit(
                        value = current.showers,
                        unit = currentUnits.showers.orEmpty()
                    ),
                    snowFall = WeatherUnit(
                        value = current.snowfall,
                        unit = currentUnits.snowfall.orEmpty()
                    ),
                    temperature = WeatherUnit(
                        value = current.temperature2m,
                        unit = currentUnits.temperature2m.orEmpty()
                    ),
                    time = current.time,
                    weatherCode = current.weatherCode.toWeatherCode(),
                    windSpeed = WeatherUnit(
                        value = current.windSpeed10m,
                        unit = currentUnits.windSpeed10m.orEmpty()
                    )
                ),
                upcomingDays = getUpcomingDays(daily = daily, dailyUnits = dailyUnits),
            )
        }
    }

    private fun getUpcomingDays(
        daily: Daily,
        dailyUnits: DailyUnits,
    ): List<DailyForecast> {
        return try {
            daily.time.mapIndexed { index, time ->
                DailyForecast(
                    date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(time), zoneId),
                    maxRealFeel = WeatherUnit(
                        value = daily.apparentTemperatureMax[index],
                        unit = dailyUnits.apparentTemperatureMax.orEmpty()
                    ),
                    maxTemperature = WeatherUnit(
                        value = daily.temperature2mMax[index],
                        unit = dailyUnits.apparentTemperatureMax.orEmpty()
                    ),
                    minRealFeel = WeatherUnit(
                        value = daily.apparentTemperatureMin[index],
                        unit = dailyUnits.apparentTemperatureMin.orEmpty()
                    ),
                    minTemperature = WeatherUnit(
                        value = daily.temperature2mMin[index],
                        unit = dailyUnits.temperature2mMin.orEmpty()
                    ),
                    sunrise = OffsetDateTime.ofInstant(
                        Instant.ofEpochSecond(daily.sunrise[index]), zoneId
                    ),
                    sunset = OffsetDateTime.ofInstant(
                        Instant.ofEpochSecond(daily.sunset[index]), zoneId
                    ),
                    weatherCode = daily.weatherCode[index].toWeatherCode()
                )
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.e("ApiForecastMapper", e.message, e)
            emptyList()
        }
    }

    private fun Int?.toWeatherCode(): WeatherCode? =
        WeatherCode.entries.firstOrNull { it.value == this }

}
