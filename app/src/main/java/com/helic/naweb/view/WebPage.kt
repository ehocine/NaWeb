package com.helic.naweb.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.helic.naweb.R
import com.helic.naweb.components.NoInternetScreen
import com.helic.naweb.core.ConnectivityObserver
import com.helic.naweb.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WebPage(mainViewModel: MainViewModel) {

    val hasInternetCnx by mainViewModel.networkConnectivity.collectAsState(initial = ConnectivityObserver.Status.Unavailable)
    val isRefreshing by mainViewModel.isRefreshing.collectAsState()
    var launchedRefresh by remember { mutableStateOf(false) }
    val navigator = rememberWebViewNavigator()
    var firstLoading by remember { mutableStateOf(true) }
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                Log.d("Accompanist WebView", "Page started loading for $url")
            }
        }
    }

    val webViewState = rememberWebViewState(
        url = "https://codecanyon.net/category/mobile/android"
    )

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            launchedRefresh = true
            navigator.reload()
        })
    {
        Scaffold {
            Box(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                when (hasInternetCnx) {
                    ConnectivityObserver.Status.Available -> {
                        WebView(
                            state = webViewState,
                            modifier = Modifier
                                .fillMaxWidth(),
                            navigator = navigator,
                            onCreated = { webView ->
                                true.also { webView.settings.javaScriptEnabled = it }
                            },
                            client = webClient
                        )
                        when {
                            webViewState.isLoading -> {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primary
                                )
                                if (!firstLoading && launchedRefresh) {
                                    LaunchedEffect(key1 = Unit) {
                                        mainViewModel.isRefreshing.emit(true)
                                    }
                                }
                            }
                            !webViewState.isLoading -> {
                                launchedRefresh = false
                                firstLoading = false
                                LaunchedEffect(key1 = Unit) {
                                    mainViewModel.isRefreshing.emit(false)
                                }
                            }
                        }
                    }
                    ConnectivityObserver.Status.Unavailable -> {
                        NoInternetScreen()
                    }
                    ConnectivityObserver.Status.Lost -> {

                    }
                    else -> Unit
                }
                AndroidView(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    factory = { context ->
                        AdView(context).apply {
                            setAdSize(AdSize.BANNER)
                            adUnitId = context.getString(R.string.ad_id_banner)
                            loadAd(AdRequest.Builder().build())
                        }
                    }
                )
            }
        }
    }

}