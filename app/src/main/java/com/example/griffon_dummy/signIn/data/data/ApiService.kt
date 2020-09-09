package com.example.griffon_dummy.signIn.data.data

import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import com.example.griffon_dummy.signUp.data.entity.NewUserBody

import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @GET("/api/v1/mgmt/client-info")
    fun getClientInfo(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): Observable<ClientInfo>

    @FormUrlEncoded
    @POST("/api/v1/oauth/token")
    @Headers("Content-type: application/x-www-form-urlencoded")
    fun grantNewAccessToken(
        @Field("grant_type") type: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("username") username: String? = null,
        @Field("password") password: String? = null
    ): Observable<AccessToken>

}