package com.example.griffon_dummy.resetPassword.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ReceivedMessage(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status :Int,
    @SerializedName("add_info") val add_info : String,
    @SerializedName("sid") val sid: String
):Parcelable