package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class AccessToken(
    @SerializedName("token_type") val tokenType: String = "",
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("expiration_date") val expirationDate: Calendar? = null
) : Parcelable {
    fun isExpired(): Boolean =
        expirationDate != null &&
                Calendar.getInstance().after(expirationDate)
}