package com.example.griffon_dummy.resetPassword.entity

import com.google.gson.annotations.SerializedName

data class ConfirmCode(
    @SerializedName("sid") val sid : String,
    @SerializedName("client_id") val client_id : String,
    @SerializedName("code") val code:String
)