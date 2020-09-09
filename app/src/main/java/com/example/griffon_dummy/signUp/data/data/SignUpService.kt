package com.example.griffon_dummy.signUp.data.data

import com.example.griffon_dummy.signUp.data.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpService {
    @POST("api/v1/oauth/signup/phone/verify")
    fun phoneVerify(
        @Query("sid") sid: String,
        @Body smsCodeBody: SmsCodeRequestBody
    ): Single<PhoneResponse>

    @POST("api/v1/oauth/signup")
    fun signUp(
        @Header("Content-Type") contentType: String,
        @Body body: NewUserBody
    ) : Observable<AccessToken>

    @POST("api/v1/oauth/signup")
    fun signUpWithPhone(
        @Header("Content-Type") contentType: String,
        @Body body: NewUserBody
    ) : Observable<PhoneRegister>

    @POST("api/v1/oauth/register")
    fun registerWithPassword(
        @Query("sid") sid: String,
        @Body passwordBody: PasswordRequestBody
    ): Observable<AccessToken>

    @POST("api/v1/oauth/signup/phone/resend")
    fun resendOtp(
        @Query("sid") sid: String
    ): Completable
}