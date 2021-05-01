package com.example.griffon_dummy.signIn.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ButtonColor(
    @SerializedName("type") val type: String,
    @SerializedName("gradient_type") val gradientType: String?,
    @SerializedName("color") val colors: List<String>,
    @SerializedName("animation") val animation: Boolean
) : Parcelable