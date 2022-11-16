package com.helic.naweb.model

import com.google.gson.annotations.SerializedName

data class Configs(
    @SerializedName("url")
    val url: String,
    @SerializedName("showTopAppBar")
    val showTopAppBar: Boolean,
    @SerializedName("topAppBarTitle")
    val topAppBarTitle: String,
    @SerializedName("topAppBarTitleColor")
    val topAppBarTitleColor: String,
    @SerializedName("topAppColor")
    val topAppColor: String,
    @SerializedName("spinnerColor")
    val spinnerColor: String,
    @SerializedName("systemUIColor")
    val systemUIColor: String,
    @SerializedName("admobInterstitialID")
    val admobInterstitialID: String,
    @SerializedName("admobBannerID")
    val admobBannerID: String,
    @SerializedName("showInterstitialAds")
    val showInterstitialAds: Boolean,
    @SerializedName("showBannerAds")
    val showBannerAds: Boolean,
    @SerializedName("refreshIcon")
    val refreshIcon: Boolean,
    @SerializedName("refreshIconColor")
    val refreshIconColor: String,

)
