package com.helic.naweb.navigation

sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens(route = "splash_screen")
    object WebPage : AppScreens(route = "web_page")
}