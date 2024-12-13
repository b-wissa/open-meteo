package com.tom.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tom.weather.navigation.NavigationDestination
import com.tom.weather.ui.latest.LatestWeatherView
import com.tom.weather.ui.theme.WeatherTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                WeatherTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        AppNav(navHostController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppNav(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavigationDestination.LatestWeather.route
    ) {
        composable(route = NavigationDestination.LatestWeather.route) {
            LatestWeatherView()
        }
    }
}
