package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasswordRequestBody(
    val password: String
) : Parcelable