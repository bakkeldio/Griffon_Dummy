package com.example.griffon_dummy

import com.example.griffon_dummy.dataClasses.AccessToken
import com.example.griffon_dummy.dataClasses.NewUserBody
import com.example.griffon_dummy.dataClasses.SmsCodeRequestBody
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpService {
    @POST("api/v1/oauth/signup/phone/verify")
    fun phoneVerify(
        @Query("sid") sid: String,
        @Body smsCodeBody: SmsCodeRequestBody
    ): Completable

    @POST("api/v1/oauth/signup")
    fun signUp(
        @Header("Content-Type") contentType: String,
        @Body body: NewUserBody
    ) : Observable<AccessToken>
}