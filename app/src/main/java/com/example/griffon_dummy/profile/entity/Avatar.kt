package com.example.griffon_dummy.profile.entity

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("original")val original : String,
    @SerializedName("normal") val normal : String,
    @SerializedName( "thumbnail")val thumbnail: String)