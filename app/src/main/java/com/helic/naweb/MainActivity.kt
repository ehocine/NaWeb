package com.helic.naweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.helic.naweb.ui.theme.NaWebTheme
import com.helic.naweb.utils.loadInterstitial
import com.helic.naweb.view.WebPage
import com.helic.naweb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaWebTheme {
                val systemUiController = rememberSystemUiController()
                val systemUIColor = Color.Black
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = systemUIColor
                    )
                }
                WebPage(mainViewModel)
            }
        }
        loadInterstitial(this)
    }
}
