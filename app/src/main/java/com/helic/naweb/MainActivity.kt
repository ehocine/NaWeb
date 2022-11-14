package com.helic.naweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helic.naweb.navigation.NavGraph
import com.helic.naweb.ui.theme.NaWebTheme
import com.helic.naweb.utils.loadInterstitial
import com.helic.naweb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaWebTheme {
                navController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                val systemUIColor = mainViewModel.systemUIColor.value
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = systemUIColor
                    )
                }
                NavGraph(navController = navController, mainViewModel = mainViewModel)
            }
        }
        loadInterstitial(this)
    }
}
