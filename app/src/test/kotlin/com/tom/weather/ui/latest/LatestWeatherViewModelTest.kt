package com.tom.weather.ui.latest

import app.cash.turbine.test
import com.tom.weather.TestCoroutineRule
import com.tom.weather.common.model.Forecast
import com.tom.weather.common.model.LatLngLocation
import com.tom.weather.latest.usecase.GetLatestWeatherUseCase
import com.tom.weather.latest.usecase.LatestWeather
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState
import com.tom.weather.ui.latest.LatestWeatherViewModel.ViewState.ForecastState

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class LatestWeatherViewModelTest {
    @get:Rule
    val coroutineRule = TestCoroutineRule()
    private val firstLocation = LatLngLocation(latitude = 45.013, longitude = 12.31)
    private val secondLocation = LatLngLocation(latitude = 53.013, longitude = 13.31)

    private val latestWeatherFlow = MutableSharedFlow<LatestWeather>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    private val getLatestWeatherUseCase: GetLatestWeatherUseCase = mockk {
        every { this@mockk() } returns latestWeatherFlow
    }

    private lateinit var viewModel: LatestWeatherViewModel


    @Before
    fun setup() {
        mockkObject(WeatherInfoMapper)
    }

    @After
    fun tearDown() {
        unmockkObject(WeatherInfoMapper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `gets latest weather`() = runTest {
        init()
        // wait until collection in is ready
        advanceUntilIdle()

        // initial state
        viewModel.viewState.test {
            val initialState = awaitItem()
            assertEquals(
                ViewState(
                    location = null,
                    forecastState = ForecastState.Loading,
                ), initialState
            )

            // receives updates from use case
            val firstForecast: Forecast = mockk()
            val forecastReadyMock: ForecastState.Ready = mockk()
            every {
                WeatherInfoMapper.map(LatestWeather.WeatherState.Ready(forecast = firstForecast))
            } returns forecastReadyMock

            latestWeatherFlow.emit(
                LatestWeather(
                    indexedLatLngLocation = 0 to firstLocation,
                    weatherState = LatestWeather.WeatherState.Ready(forecast = firstForecast)
                )
            )
            val actualFirstState = awaitItem()
            assertEquals(
                ViewState(
                    location = 1 to firstLocation,
                    forecastState = forecastReadyMock,
                ), actualFirstState
            )

            // recieve second location forecast
            // receives updates from use case
            val secondForecast: Forecast = mockk()
            val secondForecastReadyMock: ForecastState.Ready = mockk()
            every {
                WeatherInfoMapper.map(LatestWeather.WeatherState.Ready(forecast = secondForecast))
            } returns forecastReadyMock

            latestWeatherFlow.emit(
                LatestWeather(
                    indexedLatLngLocation = 2 to secondLocation,
                    weatherState = LatestWeather.WeatherState.Ready(forecast = secondForecast)
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `on Error fetching forecast displays error`() = runTest {
        init()
        advanceUntilIdle()
        viewModel.viewState.test {
            skipItems(1)
            latestWeatherFlow.emit(
                LatestWeather(
                    indexedLatLngLocation = 0 to firstLocation,
                    weatherState = LatestWeather.WeatherState.Error,
                )
            )
            every { WeatherInfoMapper.map(LatestWeather.WeatherState.Error) } returns ForecastState.Error

            val actual = awaitItem()
            assertEquals(
                ViewState(
                    location = 1 to firstLocation,
                    forecastState = ForecastState.Error,
                ), actual
            )
        }
    }

    private fun init() {
        viewModel = LatestWeatherViewModel(getLatestWeatherUseCase = getLatestWeatherUseCase)
    }
}
