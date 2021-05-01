package com.example.griffon_dummy.resetPassword.entity

import com.google.gson.annotations.SerializedName

data class UpdatePasswordForm(
    @SerializedName("new_password") val new_password: String
)