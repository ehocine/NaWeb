package com.helic.naweb.utils

import androidx.compose.ui.graphics.Color

object Extensions {
    val String.color
        get() = Color(android.graphics.Color.parseColor(this))
}