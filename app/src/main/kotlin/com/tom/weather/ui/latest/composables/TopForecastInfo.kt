package com.tom.weather.ui.latest.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.tom.weather.R
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.ForecastState.Ready.ConditionDescription
import com.tom.weather.ui.theme.DayColor
import com.tom.weather.ui.theme.NightColor

@Composable
internal fun TopForecastInfo(forecastState: ViewState.ForecastState) {
    val animatedColor by animateColorAsState(
        targetValue = when {
            (forecastState is ViewState.ForecastState.Ready) -> {
                if (forecastState.isDay) {
                    DayColor
                } else {
                    NightColor
                }
            }

            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        label = "color"
    )
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = animatedColor
        ),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            when (forecastState) {
                ViewState.ForecastState.Error -> {
                    Text(
                        text = stringResource(R.string.could_not_load_location_forecast)
                    )
                }

                is ViewState.ForecastState.Ready -> {
                    CurrentWeather(
                        realFeel = forecastState.currentRealFeel,
                        subtitle = forecastState.subtitle,
                        description = forecastState.conditionDescription,
                        windSpeed = forecastState.wind,
                        time = forecastState.time,
                    )
                }

                ViewState.ForecastState.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

    }
}

@Composable
private fun CurrentWeather(
    realFeel: String,
    subtitle: Int?,
    description: ConditionDescription?,
    windSpeed: String?,
    time: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = realFeel,
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            subtitle?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            description?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        append(stringResource(it.title))
                        append(": ")
                        append(it.value)
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            windSpeed?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.wind_speed, it)

                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.End),
                text = time,
            )
        }
    }
}
