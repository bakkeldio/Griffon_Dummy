package com.example.griffon_dummy.signUp.data.data

import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import com.example.griffon_dummy.signUp.data.entity.PhoneRegister
import com.example.griffon_dummy.signUp.data.entity.PhoneResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SignUpRepository(private val signUpLocal: SignUpLocalI, private val remoteDataSource: RemoteDataI) : SignUpRepositoryI{
    override fun getClientInfo(): ClientInfo{
        return signUpLocal.getClientInfo()
    }

    override fun getAccessToken(username: String, password: String): Observable<AccessToken> {
        return remoteDataSource.getAccessToken(username, password).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSID(phoneNumber: String): Observable<PhoneRegister> {
        return remoteDataSource.getSID(phoneNumber)
    }

    override fun verifyPhone(sid: String, code: String): Observable<PhoneResponse> {
        return remoteDataSource.verifyPhone(sid, code).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun register(sid: String, password: String): Observable<AccessToken> {
        return remoteDataSource.register(password, sid).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
interface SignUpRepositoryI{
    fun getClientInfo(): ClientInfo
    fun getAccessToken(username :String, password: String): Observable<AccessToken>
    fun getSID(phoneNumber: String):Observable<PhoneRegister>
    fun verifyPhone(sid : String, code :String) : Observable<PhoneResponse>
    fun register(sid: String, password: String) : Observable<AccessToken>
}