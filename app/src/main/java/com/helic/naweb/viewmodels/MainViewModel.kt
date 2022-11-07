package com.helic.naweb.viewmodels

import android.app.Application
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.helic.naweb.core.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver,
    application: Application
) : AndroidViewModel(application) {

    val networkConnectivity = networkConnectivityObserver.observe()

    val isRefreshing = MutableStateFlow(false)

}