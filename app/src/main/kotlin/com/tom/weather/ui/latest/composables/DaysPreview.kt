package com.tom.weather.ui.latest.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tom.weather.R
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.Forecast
import com.tom.weather.ui.theme.WeatherTheme

@Composable
internal fun ColumnScope.DaysPreview(forecast: Forecast) {
    when (forecast) {

        Forecast.Error,
        Forecast.Loading -> {
        }

        is Forecast.Ready -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(forecast.days) { dayPreview ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 42.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        with(dayPreview) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = date,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(
                                            R.string.real_feel_max_short,
                                            realFeelMax
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(
                                            R.string.real_feel_min_short,
                                            realFeelMax
                                        )
                                    )
                                }
                                description?.let {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = stringResource(it))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDaysPreview() {
    WeatherTheme {
        Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            DaysPreview(
                Forecast.Ready(
                    currentRealFeel = "13.5 C",
                    subtitle = null,
                    conditionDescription = null,
                    wind = null,
                    time = "time",
                    isDay = true,
                    days = listOf(
                        Forecast.Ready.DayPreview(
                            date = "Mon 13.02",
                            realFeelMax = "10 C",
                            realFeelMin = "3 C",
                            maximumTemp = "12 C",
                            minTemp = "5 C",
                            description = R.string.slight_rain
                        )
                    )
                )
            )
        }
    }
}
