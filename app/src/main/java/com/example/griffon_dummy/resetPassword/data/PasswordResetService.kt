package com.example.griffon_dummy.resetPassword.data

import com.example.griffon_dummy.resetPassword.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface PasswordResetService {

    @Headers("Content-Type: application/json")
    @POST("api/v1/oauth/password/reset")
    fun postReset(
        @Body body: RequestOTP
    ):Observable<ReceivedMessage>

    @PUT("api/v1/oauth/password/reset")
    fun resendOTP(
        @Body body: RequestOTP
    )
    : Observable<ReceivedMessage>

    @Headers("Content-type: application/json")
    @POST("api/v1/oauth/password/reset/verify")
    fun verifyEmail(
        @Query("sid") sid :String,
        @Query("code") code: String,
        @Query("client_id") client_id : String
    ):  Observable<ConfirmationResponse>


    @FormUrlEncoded
    @PUT("api/v1/oauth/password/reset")
    fun updatePassword(
        @Query("client_id") client_id: String,
        @Query("sid") sid : String,
        @Field("new_password") new_password :String
    ): Observable<UpdatePasswordConfirmation>

}