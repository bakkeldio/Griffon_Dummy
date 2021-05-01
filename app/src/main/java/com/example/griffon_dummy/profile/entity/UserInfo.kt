package com.example.griffon_dummy.profile.entity

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("id") val id : String,
    @SerializedName("bucket_id") val bucket_id : String,
    @SerializedName("brand_id") val brand_id :String,
    @SerializedName("email") val email : String,
    @SerializedName("email_verified") val email_verified : Boolean,
    @SerializedName("pin") val pin: String,
    @SerializedName("password") val password : String,
    @SerializedName("mfa_type")val mfa_type : String,
    @SerializedName("avatar") val avatar: Avatar
)