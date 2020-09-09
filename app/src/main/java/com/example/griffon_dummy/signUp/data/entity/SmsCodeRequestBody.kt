package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmsCodeRequestBody(
    val code: String
) : Parcelable
