package com.example.griffon_dummy.dataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewUserBody(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String? = null
) : Parcelable