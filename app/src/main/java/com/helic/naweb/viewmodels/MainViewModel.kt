package com.helic.naweb.viewmodels

import android.util.Log
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
    val spinnerColor: MutableState<Color> = mutableStateOf(Color.White)
    val systemUIColor: MutableState<Color> = mutableStateOf(Color.White)

    fun getConfigs(): Boolean {
        return try {
            val configsJson =
                application.resources.openRawResource(R.raw.configs).bufferedReader()
                    .use { it.readText() }
            val gson = Gson()
            val configs = gson.fromJson(configsJson, Configs::class.java)
            url.value = configs.url
            spinnerColor.value = configs.spinnerColor.color
            systemUIColor.value = configs.systemUIColor.color
            Log.d("Url", "Read url: ${url.value}")
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}