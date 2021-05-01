package com.example.griffon_dummy.resetPassword.data

import com.example.griffon_dummy.resetPassword.entity.ConfirmationResponse
import com.example.griffon_dummy.resetPassword.entity.ReceivedMessage
import com.example.griffon_dummy.resetPassword.entity.UpdatePasswordConfirmation
import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ResetRepository(private val remoteDataI: RemoteDataI,
                    private val localDataI: LocalDataI) : ResetRepositoryI{
    override fun getSP(): ClientInfo {
        return localDataI.getSP()
    }

    override fun getOtpForUsername(username: String, reset_option: String
    ): Observable<ReceivedMessage> {
        return remoteDataI.getMessageForUsername(username,reset_option).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getConfirmation(sid: String,code: String): Observable<ConfirmationResponse> {
        return remoteDataI.getConfirmation(sid, code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpdatePasswordConfirmation(code: String, newPassword: String
    ): Observable<UpdatePasswordConfirmation> {
        return remoteDataI.getUpdatePasswordConfirmation(code, newPassword).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
interface ResetRepositoryI{
    fun getSP() : ClientInfo
    fun getOtpForUsername( username : String, reset_option: String): Observable<ReceivedMessage>
    fun getConfirmation(sid : String,code : String) : Observable<ConfirmationResponse>
    fun getUpdatePasswordConfirmation(code: String, newPassword:String):Observable<UpdatePasswordConfirmation>
}