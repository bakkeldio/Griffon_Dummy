package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhoneResponse(
    @SerializedName("redirect_uri") val redirect_uri : String,
    @SerializedName("sid") val sid : String): Parcelable