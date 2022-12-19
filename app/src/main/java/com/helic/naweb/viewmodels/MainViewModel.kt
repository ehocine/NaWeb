package com.helic.naweb.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.helic.naweb.NaWebApp
import com.helic.naweb.R
import com.helic.naweb.core.NetworkConnectivityObserver
import com.helic.naweb.model.Configs
import com.helic.naweb.utils.Extensions.color
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: NaWebApp,
    networkConnectivityObserver: NetworkConnectivityObserver,
) : ViewModel() {

    val networkConnectivity = networkConnectivityObserver.observe()
    val isRefreshing = MutableStateFlow(false)

    val url: MutableState<String> = mutableStateOf("")
    val showTopAppBar: MutableState<Boolean> = mutableStateOf(true)
    val topAppBarTitle: MutableState<String> = mutableStateOf("")
    val topAppBarTitleColor: MutableState<Color> = mutableStateOf(Color.White)
    val spinnerColor: MutableState<Color> = mutableStateOf(Color.Black)
    val topAppColor: MutableState<Color> = mutableStateOf(Color.White)
    val systemUIColor: MutableState<Color> = mutableStateOf(Color.White)
    val showBannerAds: MutableState<Boolean> = mutableStateOf(true)
    val showInterstitialAds: MutableState<Boolean> = mutableStateOf(true)
    val admobBannerID: MutableState<String> = mutableStateOf("")
    val admobInterstitialID: MutableState<String> = mutableStateOf("")
    val refreshIcon: MutableState<Boolean> = mutableStateOf(true)
    val refreshIconColor: MutableState<Color> = mutableStateOf(Color.White)

    fun getConfigs(): Boolean {
        return try {
            val configsJson =
                application.resources.openRawResource(R.raw.configs).bufferedReader()
                    .use { it.readText() }
            val gson = Gson()
            val configs = gson.fromJson(configsJson, Configs::class.java)
            url.value = configs.url
            showTopAppBar.value = configs.showTopAppBar
            if (showTopAppBar.value) {
                topAppBarTitle.value = configs.topAppBarTitle
                topAppColor.value = configs.topAppColor.color
                topAppBarTitleColor.value = configs.topAppBarTitleColor.color
                refreshIcon.value = configs.refreshIcon
                if (refreshIcon.value) {
                    refreshIconColor.value = configs.refreshIconColor.color
                }
            }
            spinnerColor.value = configs.spinnerColor.color
            systemUIColor.value = configs.systemUIColor.color
            showBannerAds.value = configs.showBannerAds
            if (showBannerAds.value) admobBannerID.value = configs.admobBannerID
            showInterstitialAds.value = configs.showInterstitialAds
            if (showInterstitialAds.value) admobInterstitialID.value = configs.admobInterstitialID
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}