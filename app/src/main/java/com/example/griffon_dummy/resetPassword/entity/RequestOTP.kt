package com.example.griffon_dummy.resetPassword.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestOTP(
    @SerializedName("client_id") val client_id :String?,
    @SerializedName("username") val username: String,
    @SerializedName("reset_option") val reset_option : String
):Parcelable