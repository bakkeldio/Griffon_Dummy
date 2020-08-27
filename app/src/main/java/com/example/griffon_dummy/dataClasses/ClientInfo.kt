package com.example.griffon_dummy.dataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClientInfo (
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("bucket") val bucket: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("name") val name: String?,
    @SerializedName("background_color") val backgroundColor: String?,
    @SerializedName("button_color") val buttonColor: ButtonColor?,
    @SerializedName("logo_image") val logoImage: String?,
    @SerializedName("terms_condition_url") val termsConditionUrl: String?,
    @SerializedName("sign_up_type") val signUpType: ArrayList<SignUpType>?
) : Parcelable