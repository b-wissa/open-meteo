package com.tom.weather.ui.latest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tom.weather.ui.latest.composables.CurrentForecast
import org.koin.androidx.compose.koinViewModel

@Composable
fun LatestWeatherView() {
    val viewModel: LatestWeatherViewModel = koinViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        CurrentForecast(viewState = state)
    }

}
