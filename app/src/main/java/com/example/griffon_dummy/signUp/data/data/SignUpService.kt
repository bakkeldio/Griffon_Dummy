package com.example.griffon_dummy.signUp.data.data

import com.example.griffon_dummy.signUp.data.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface SignUpService {
    @POST("api/v1/oauth/signup/phone/verify")
    fun phoneVerify(
        @Query("sid") sid: String,
        @Body smsCodeBody: SmsCodeRequestBody
    ): Observable<PhoneResponse>


    //registration with email
    @POST("api/v1/oauth/signup")
    fun signUp(
        @Body body: NewUserBody
    ) : Observable<AccessToken>


    //requestOtp and resendOtp
    @Headers("Content-type: application/json")
    @POST("api/v1/oauth/signup")
    fun signUpWithPhone(
        @Body body: NewUserBody
    ) : Observable<PhoneRegister>

    //registration with phone number
    @POST("api/v1/oauth/register")
    fun registerWithPassword(
        @Query("sid") sid: String,
        @Body passwordBody: PasswordRequestBody
    ): Observable<AccessToken>


}