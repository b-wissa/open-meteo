package com.tom.weather.ui.latest

import com.tom.weather.R
import com.tom.weather.common.model.CurrentForecast
import com.tom.weather.common.model.WeatherCode
import com.tom.weather.latest.usecase.LatestWeather
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.Forecast
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.Forecast.Ready.ConditionDescription
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.Forecast.Ready.DayPreview
import java.time.format.DateTimeFormatter

object WeatherInfoMapper {
    private const val CURRENT_TIME_FORMAT = "HH:mm"
    private const val DATE_FORMAT = "EEE dd.MM"
    private val hourMinuteFormatter = DateTimeFormatter.ofPattern(CURRENT_TIME_FORMAT)
    private val dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    fun map(weatherState: LatestWeather.WeatherState): Forecast {
        return when (weatherState) {
            LatestWeather.WeatherState.Error -> {
                Forecast.Error
            }

            LatestWeather.WeatherState.Loading -> {
                Forecast.Loading
            }

            is LatestWeather.WeatherState.Ready -> {
                with(weatherState.forecast) {
                    return@with Forecast.Ready(
                        currentRealFeel = "${currentForecast.realFeel}",
                        subtitle = currentForecast.weatherCode.toResource(),
                        conditionDescription = currentForecast.getCondition(),
                        wind = currentForecast.windSpeed.toString(),
                        time = hourMinuteFormatter.format(currentForecast.time),
                        isDay = currentForecast.isDay,
                        days = upcomingDays.map {
                            DayPreview(
                                date = dateFormatter.format(it.date),
                                realFeelMax = it.maxRealFeel.toString(),
                                realFeelMin = it.minRealFeel.toString(),
                                maximumTemp = it.maxTemperature.toString(),
                                minTemp = it.minTemperature.toString(),
                                description = it.weatherCode.toResource()
                            )
                        }
                    )
                }
            }
        }
    }

    private fun CurrentForecast.getCondition() = when {
        (rain.value > 0) -> {
            ConditionDescription(
                title = R.string.rain,
                value = rain.toString()
            )
        }

        (showers.value > 0) -> {
            ConditionDescription(
                title = R.string.showers,
                value = showers.value.toString()
            )
        }

        (snowFall.value > 0) -> {
            ConditionDescription(
                title = R.string.snow_fall,
                value = snowFall.value.toString()
            )
        }

        else -> {
            null
        }
    }


    private fun WeatherCode?.toResource(): Int? = when (this) {
        WeatherCode.CLEAR_SKY -> R.string.clear_sky
        WeatherCode.MAINLY_CLEAR -> R.string.mainly_clear
        WeatherCode.PARTLY_CLOUDY -> R.string.partly_cloudy
        WeatherCode.OVERCAST -> R.string.overcast
        WeatherCode.FOG -> R.string.fog
        WeatherCode.DEPOSITING_RIME_FOG -> R.string.depositing_rime_fog
        WeatherCode.DRIZZLE_LIGHT -> R.string.light_drizzle
        WeatherCode.DRIZZLE_MODERATE -> R.string.moderate_drizzle
        WeatherCode.DRIZZLE_DENSE_INTENSITY -> R.string.dense_intensity_drizzle
        WeatherCode.FREEZING_DRIZZLE_LIGHT -> R.string.light_freezing_drizzle
        WeatherCode.FREEZING_DRIZZLE_DENSE -> R.string.dense_freezing_drizzle
        WeatherCode.RAIN_SLIGHT -> R.string.slight_rain
        WeatherCode.RAIN_MODERATE -> R.string.moderate_rain
        WeatherCode.RAIN_HEAVY -> R.string.heavy_rain
        WeatherCode.FREEZING_RAIN_LIGHT -> R.string.light_freezing_rain
        WeatherCode.FREEZING_RAIN_HEAVY -> R.string.heavy_freezing_rain
        WeatherCode.SNOW_FALL_SLIGHT -> R.string.slight_snow_fall
        WeatherCode.SNOW_FALL_MODERATE -> R.string.moderate_snow_fall
        WeatherCode.SNOW_FALL_HEAVY -> R.string.heavy_snow_fall
        WeatherCode.SNOW_GRAINS -> R.string.snow_grains
        WeatherCode.RAIN_SHOWERS_SLIGHT -> R.string.slight_rain_showers
        WeatherCode.RAIN_SHOWERS_MODERATE -> R.string.moderate_rain_showers
        WeatherCode.RAIN_SHOWERS_VIOLENT -> R.string.violent_rain_showers
        WeatherCode.SNOW_SHOWERS_SLIGHT -> R.string.slight_snow_showers
        WeatherCode.SNOW_SHOWERS_HEAVY -> R.string.heavy_snow_showers
        WeatherCode.THUNDERSTORM_SLIGHT_OR_MODERATE -> R.string.slight_or_moderate_thunderstorm
        WeatherCode.THUNDERSTORM_WITH_SLIGHT_HAIL -> R.string.thunderstorm_with_light_hail
        WeatherCode.THUNDERSTORM_WITH_HEAVY_HAIL -> R.string.thunderstorm_with_heavy_hail
        else -> null
    }
}
