package com.helic.naweb.view

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.helic.naweb.navigation.AppScreens
import com.helic.naweb.viewmodels.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000L)

        //TODO: Later, navigate to the web page screen when the configs are loaded successfully form  the JSON file.
        if (mainViewModel.getConfigs()) {
            navController.navigate(AppScreens.WebPage.route) {
                launchSingleTop = true
            }
        } else {
            Log.d("Tag", "Something went wrong")
        }
    }
    SplashContent(alpha = alphaAnim.value, mainViewModel = mainViewModel)
}

@Composable
fun SplashContent(alpha: Float, mainViewModel: MainViewModel) {

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            Image(
//                painter = painterResource(id = R.drawable.qloga_logo),
//                modifier = Modifier.alpha(alpha),
//                contentDescription = ""
//            )
            CircularProgressIndicator(
                color = mainViewModel.spinnerColor.value,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}