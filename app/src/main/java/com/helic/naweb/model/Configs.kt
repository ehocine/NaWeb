package com.helic.naweb.model

import com.google.gson.annotations.SerializedName

data class Configs(
    @SerializedName("url")
    val url: String,
    @SerializedName("spinnerColor")
    val spinnerColor: String,
    @SerializedName("systemUIColor")
    val systemUIColor: String
)
