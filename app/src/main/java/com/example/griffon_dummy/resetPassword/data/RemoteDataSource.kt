package com.example.griffon_dummy.resetPassword.data

import com.example.griffon_dummy.resetPassword.entity.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

const val client_id = "5ea469f9-a517-427b-8b16-290e9e57bb7f"

class RemoteDataSource(private val service: PasswordResetService): RemoteDataI {
    override fun getMessageForUsername(
        username: String
    ): Observable<ReceivedMessage> {
        return service.postReset(RequestOTP(client_id,  username))
    }


    override fun getConfirmation(sid :String,code: String): Observable<ConfirmationResponse> {
        return service.verifyEmail(sid, code, client_id)
    }

    override fun getUpdatePasswordConfirmation(code: String, newPassword: String): Completable {
        return service.updatePassword(code, client_id, newPassword)
    }

    override fun getOtpFromResend(
        username: String
    ): Observable<ReceivedMessage> {
        return service.resendOtp(RequestOTP(client_id,  username))
    }
}
interface RemoteDataI{
    fun getMessageForUsername(username :String): Observable<ReceivedMessage>
    fun getConfirmation(sid: String, code: String) : Observable<ConfirmationResponse>
    fun getUpdatePasswordConfirmation(code: String, newPassword:String):Completable
    fun getOtpFromResend(username: String):Observable<ReceivedMessage>
}