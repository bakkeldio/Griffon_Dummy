package com.example.griffon_dummy.resetPassword.data

import com.example.griffon_dummy.resetPassword.entity.*
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface PasswordResetService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/oauth/password/reset")
    fun postReset(
        @Body body: RequestOTP
    ):Observable<ReceivedMessage>

    @Headers("Content-Type: application/json")
    @POST("api/v1/oauth/password/reset")
    fun resendOtp(
        @Body body: RequestOTP
    ):Observable<ReceivedMessage>


    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("api/v1/oauth/password/reset/verify")
    fun verifyEmail(
        @Query("sid") sid :String,
        @Query("code") code: String,
        @Query("client_id") client_id : String
    ):  Observable<ConfirmationResponse>

    @FormUrlEncoded
    @PUT("api/v1/oauth/password/reset")
    fun updatePassword(
        @Field("code") code : String,
        @Field("client_id") client_id: String,
        @Field("new_password") new_password: String
    ): Completable

}