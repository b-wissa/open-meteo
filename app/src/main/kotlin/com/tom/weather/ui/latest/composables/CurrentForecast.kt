package com.tom.weather.ui.latest.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tom.weather.R
import com.tom.weather.common.model.IndexedLatLngLocation
import com.tom.weather.common.model.LatLngLocation
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.Forecast.Ready.ConditionDescription
import com.tom.weather.ui.theme.WeatherTheme


@Composable
internal fun CurrentForecast(viewState: ViewState) {
    val location = viewState.location
    if (location != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.padding(top = 24.dp))
            AnimatedContent(
                targetState = location,
                label = "location_title",
                transitionSpec = {
                    slideInVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) { fullHeight -> fullHeight } + fadeIn() togetherWith
                            slideOutVertically(animationSpec = spring(stiffness = Spring.StiffnessLow)) { fullHeight -> fullHeight } + fadeOut()
                }
            ) { indexedLatLngLocation ->
                LocationTitle(location = indexedLatLngLocation)
            }

            Spacer(modifier = Modifier.height(16.dp))
            AnimatedContent(
                targetState = viewState.forecast,
                label = "forecast",
                transitionSpec = {
                    slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                            slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
                }
            ) { forecast ->
                Column {
                    Forecast(forecast = forecast)
                }
            }

        }
    } else {
        LoadingLocation()
    }
}


@Composable
private fun ColumnScope.Forecast(forecast: ViewState.Forecast) {
    TopForecastInfo(forecast = forecast)
    Spacer(modifier = Modifier.height(16.dp))
    DaysPreview(forecast = forecast)
}

@Composable
private fun LocationTitle(location: IndexedLatLngLocation) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(
            R.string.location_with_index_title, location.first,
            location.second.latitude, location.second.longitude,
        ),
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
@Preview
private fun PreviewCurrentForecast() {
    WeatherTheme {
        CurrentForecast(
            viewState = ViewState(
                location =
                1 to LatLngLocation(
                    latitude = 68.69,
                    longitude = 17.71
                ),
                forecast = ViewState.Forecast.Ready(
                    currentRealFeel = "3.5 C",
                    subtitle = R.string.snow_fall,
                    conditionDescription = ConditionDescription(
                        title = R.string.showers,
                        value = "3 mm"
                    ),
                    wind = "10 km/s",
                    time = "23:12",
                    isDay = true,
                    days = listOf()
                ),
            )
        )
    }
}
