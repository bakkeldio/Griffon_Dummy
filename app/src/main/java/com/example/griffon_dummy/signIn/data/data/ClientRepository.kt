package com.example.griffon_dummy.signIn.data.data

import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ClientRepository(private val remoteDataI: RemoteDataI,
                       private val localDataI: LocalDataI) : ClientRepoI {
    override fun getClientInfo(): Observable<ClientInfo> {
        return remoteDataI.getClientInfo()
            .map {
                localDataI.saveClient(it)
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAccessToken(username: String, password: String): Observable<AccessToken> {
        return remoteDataI.getAccessToken(username, password)
    }

}
interface ClientRepoI{
    fun getClientInfo():Observable<ClientInfo>
    fun getAccessToken(username: String, password: String) : Observable<AccessToken>
}