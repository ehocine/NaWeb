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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.helic.naweb.components.NoInternetScreen
import com.helic.naweb.core.ConnectivityObserver
import com.helic.naweb.utils.showInterstitial
import com.helic.naweb.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WebPage(mainViewModel: MainViewModel) {

    val context = LocalContext.current
    val hasInternetCnx by mainViewModel.networkConnectivity.collectAsState(initial = ConnectivityObserver.Status.Unavailable)
    val isRefreshing by mainViewModel.isRefreshing.collectAsState()
    var launchedRefresh by remember { mutableStateOf(false) }
    val navigator = rememberWebViewNavigator()
    var firstLoading by remember { mutableStateOf(true) }
    var loadingWebsite by remember { mutableStateOf(true) }
    val url by remember { mainViewModel.url }
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
        url = url
    )

    loadingWebsite = when {
        webViewState.isLoading -> {
            true
        }
        else -> {
            false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            launchedRefresh = true
            navigator.reload()
            loadingWebsite = true
        })
    {
        Scaffold(
            topBar = {
                if (mainViewModel.showTopAppBar.value) {
                    TopAppBar(
                        title = { Text(mainViewModel.topAppBarTitle.value) },
                        actions = {
                            if (mainViewModel.refreshIcon.value) {
                                IconButton(onClick = {
                                    navigator.reload()
                                    loadingWebsite = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        tint = mainViewModel.refreshIconColor.value,
                                        contentDescription = ""
                                    )
                                }
                            }
                        },
                        backgroundColor = mainViewModel.topAppColor.value,
                        contentColor = mainViewModel.topAppBarTitleColor.value,
                        elevation = 0.dp
                    )
                }
            }

        ) {
            if (mainViewModel.showInterstitialAds.value) {
                showInterstitial(context)
            }
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
                        when (loadingWebsite) {
                            true -> {
                                CircularProgressIndicator(
                                    color = mainViewModel.spinnerColor.value
                                )
                                if (!firstLoading && launchedRefresh) {
                                    LaunchedEffect(key1 = Unit) {
                                        mainViewModel.isRefreshing.emit(true)
                                    }
                                }
                            }
                            false -> {
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
                if (mainViewModel.showBannerAds.value) {
                    AndroidView(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        factory = { context ->
                            AdView(context).apply {
                                setAdSize(AdSize.BANNER)
                                adUnitId = mainViewModel.admobBannerID.value
                                loadAd(AdRequest.Builder().build())
                            }
                        }
                    )
                }
            }
        }
    }
}