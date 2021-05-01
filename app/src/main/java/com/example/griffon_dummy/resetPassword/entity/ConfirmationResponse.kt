package com.example.griffon_dummy.resetPassword.entity

import com.google.gson.annotations.SerializedName

data class ConfirmationResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status : Int,
    @SerializedName("sid") val sid : String
)