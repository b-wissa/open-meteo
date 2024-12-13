package com.tom.weather.latest.usecase

import app.cash.turbine.test
import com.tom.weather.TestCoroutineRule
import com.tom.weather.common.model.Forecast
import com.tom.weather.common.model.IndexedLatLngLocation
import com.tom.weather.common.model.LatLngLocation
import com.tom.weather.forecast.repository.ForecastRepository
import com.tom.weather.location.UserLocationProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class GetLatestWeatherUseCaseTest {
    @get:Rule
    val coroutineRule = TestCoroutineRule()
    private val firstLocation = LatLngLocation(latitude = 45.013, longitude = 12.31)
    private val secondLocation = LatLngLocation(latitude = 53.013, longitude = 13.31)

    private val userLocationFlow = MutableStateFlow<IndexedLatLngLocation>(
        0 to firstLocation
    )
    private val userLocationProvider: UserLocationProvider = mockk {
        every { this@mockk() } returns userLocationFlow
    }

    private val forecastRepository: ForecastRepository = mockk()

    private val useCase = GetLatestWeatherUseCase(
        userLocationProvider = userLocationProvider,
        forecastRepository = forecastRepository,
    )

    @Test
    fun `fetches forecast when user location is pushed`() = runTest {
        val mockForecast: Forecast = mockk()
        coEvery { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) } returns Result.success(
            mockForecast
        )

        useCase().test {
            val actualLoading = awaitItem()

            // shows loading with correct location
            assertEquals(
                LatestWeather(
                    indexedLatLngLocation = 0 to firstLocation,
                    weatherState = LatestWeather.WeatherState.Loading
                ), actualLoading
            )
            // propagates forecast
            val actual = awaitItem()
            assertEquals(
                LatestWeather(
                    indexedLatLngLocation = 0 to firstLocation,
                    weatherState = LatestWeather.WeatherState.Ready(forecast = mockForecast)
                ),
                actual
            )

            coVerify(exactly = 1) { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetches forecast for latest location`() = runTest {
        val mockFirstForecast: Forecast = mockk()
        coEvery { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) } returns Result.success(
            mockFirstForecast
        )
        val mockSecondForecast: Forecast = mockk()
        coEvery { forecastRepository.getForecastByLocation(latLngLocation = secondLocation) } returns Result.success(
            mockSecondForecast
        )
        useCase().test {
            skipItems(2)
            userLocationFlow.emit(1 to secondLocation)
            val actualLoading = awaitItem()

            // shows loading with correct location
            assertEquals(
                LatestWeather(
                    indexedLatLngLocation = 1 to secondLocation,
                    weatherState = LatestWeather.WeatherState.Loading
                ), actualLoading
            )
            // propagates forecast
            val actual = awaitItem()
            assertEquals(
                LatestWeather(
                    indexedLatLngLocation = 1 to secondLocation,
                    weatherState = LatestWeather.WeatherState.Ready(forecast = mockSecondForecast)
                ),
                actual
            )
            coVerify(exactly = 1) { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) }
            coVerify(exactly = 1) { forecastRepository.getForecastByLocation(latLngLocation = secondLocation) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `skips fetching forecast if a new location is pushed before the fetching is finished`() =
        runTest {
            val mockFirstForecast: Forecast = mockk()
            coEvery { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) } coAnswers {
                // delay forecast fetching
                delay(2_000L)
                Result.success(
                    mockFirstForecast
                )
            }
            val mockSecondForecast: Forecast = mockk()
            coEvery { forecastRepository.getForecastByLocation(latLngLocation = secondLocation) } returns Result.success(
                mockSecondForecast
            )
            useCase().test {

                val actualFirstLoading = awaitItem()
                assertEquals(
                    LatestWeather(
                        indexedLatLngLocation = 0 to firstLocation,
                        weatherState = LatestWeather.WeatherState.Loading
                    ), actualFirstLoading
                )
                userLocationFlow.emit(1 to secondLocation)
                val actualLoading = awaitItem()

                // shows loading with correct location
                assertEquals(
                    LatestWeather(
                        indexedLatLngLocation = 1 to secondLocation,
                        weatherState = LatestWeather.WeatherState.Loading
                    ), actualLoading
                )
                // propagates forecast
                val actual = awaitItem()
                assertEquals(
                    LatestWeather(
                        indexedLatLngLocation = 1 to secondLocation,
                        weatherState = LatestWeather.WeatherState.Ready(forecast = mockSecondForecast)
                    ),
                    actual
                )
                coVerify(exactly = 1) { forecastRepository.getForecastByLocation(latLngLocation = firstLocation) }
                coVerify(exactly = 1) { forecastRepository.getForecastByLocation(latLngLocation = secondLocation) }
                cancelAndIgnoreRemainingEvents()
            }
        }
}
