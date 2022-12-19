package com.helic.naweb.view

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helic.naweb.navigation.AppScreens
import com.helic.naweb.utils.loadInterstitial
import com.helic.naweb.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import com.helic.naweb.R

@Composable
fun SplashScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000L)
        if (mainViewModel.getConfigs()) {
            if (mainViewModel.showInterstitialAds.value) {
                loadInterstitial(context, mainViewModel.admobInterstitialID.value)
            }
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
            Image(
                painter = painterResource(id = R.drawable.icon),
                modifier = Modifier.alpha(alpha),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.padding(15.dp))
            CircularProgressIndicator(
                color = mainViewModel.spinnerColor.value,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}