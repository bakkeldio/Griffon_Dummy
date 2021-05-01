package com.example.griffon_dummy.profile.data

import com.example.griffon_dummy.profile.entity.Avatar
import com.example.griffon_dummy.profile.entity.RevokeAccessTokenBody
import com.example.griffon_dummy.profile.entity.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileInterface {

    @GET("/api/v1/oauth/profile")
    fun getUserInfo(
        @Header("Authorization") token : String
    ): Observable<UserInfo>


    @Multipart
    @POST("/api/v1/oauth/profile/avatar")
    fun setAvatar(
        @Header("Authorization") token : String,
        @Part avatar: MultipartBody.Part
    ): Observable<Avatar>

    @POST("/api/v1/oauth/revoke")
    fun revokeAccessToken(
        @Body body: RevokeAccessTokenBody?
    ): Completable

}