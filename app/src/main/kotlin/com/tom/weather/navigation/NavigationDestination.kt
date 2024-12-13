package com.tom.weather.navigation

sealed class NavigationDestination(val route: String) {
    data object LatestWeather : NavigationDestination(route = "LatestWeather")
}
