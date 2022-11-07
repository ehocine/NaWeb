package com.helic.naweb.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helic.naweb.R
import com.helic.naweb.utils.MyLottieAnimation

@Composable
fun NoInternetScreen() {
    Box( contentAlignment = Alignment.Center) {
        MyLottieAnimation(modifier = Modifier.size(200.dp), lottie = R.raw.no_internet_connection)
    }
}