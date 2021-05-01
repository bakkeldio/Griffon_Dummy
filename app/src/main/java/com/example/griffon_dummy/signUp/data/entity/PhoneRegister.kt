package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhoneRegister(
    @SerializedName("redirect_uri") val redirect_uri : String,
    @SerializedName("sid") val sid : String,
    @SerializedName("add_info") val add_info : String
) : Parcelable