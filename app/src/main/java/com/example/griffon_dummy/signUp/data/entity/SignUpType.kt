package com.example.griffon_dummy.signUp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class SignUpType : Parcelable {
    @SerializedName("both") BOTH,
    @SerializedName("phone_number") PHONE,
    @SerializedName("email") EMAIL
}
