package com.example.griffon_dummy.profile.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RevokeAccessTokenBody(
    @field:SerializedName("access_token") var accessToken: String
) : Parcelable