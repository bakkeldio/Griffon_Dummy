package com.example.griffon_dummy.dataClasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SmsCodeRequestBody(
    val code: String
) : Parcelable
