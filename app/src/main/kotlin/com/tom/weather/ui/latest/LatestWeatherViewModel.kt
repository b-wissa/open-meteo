package com.tom.weather.ui.latest

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tom.weather.common.model.IndexedLatLngLocation
import com.tom.weather.latest.usecase.GetLatestWeatherUseCase
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.ForecastState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class LatestWeatherViewModel(getLatestWeatherUseCase: GetLatestWeatherUseCase) :
    ViewModel() {
    private val _viewState = MutableStateFlow<ViewState>(
        ViewState(
            location = null,
            forecastState = ForecastState.Loading,
        )
    )

    val viewState: StateFlow<ViewState> = _viewState

    init {
        getLatestWeatherUseCase()
            .onEach { latestWeather ->
                _viewState.update {
                    it.copy(
                        location = latestWeather.indexedLatLngLocation.first.plus(1)
                                to latestWeather.indexedLatLngLocation.second,
                        forecastState = WeatherInfoMapper.map(weatherState = latestWeather.weatherState)
                    )
                }

            }
            .launchIn(viewModelScope)
    }


    data class ViewState(
        val location: IndexedLatLngLocation?,
        val forecastState: ForecastState,
    ) {

        sealed interface ForecastState {
            data object Loading : ForecastState
            data object Error : ForecastState
            data class Ready(
                val currentRealFeel: String,
                @StringRes val subtitle: Int?,
                val conditionDescription: ConditionDescription?,
                val isDay: Boolean,
                val wind: String?,
                val time: String,
                val days: List<DayPreview>
            ) : ForecastState {
                data class DayPreview(
                    val date: String,
                    val realFeelMax: String,
                    val realFeelMin: String,
                    val maximumTemp: String,
                    val minTemp: String,
                    @StringRes
                    val description: Int?,
                )

                data class ConditionDescription(
                    @StringRes val title: Int,
                    val value: String,
                )
            }
        }
    }
}
