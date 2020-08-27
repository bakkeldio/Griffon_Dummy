package com.example.griffon_dummy

import com.example.griffon_dummy.dataClasses.ClientInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ClientRepository(private val remoteDataI: RemoteDataI) : ClientRepoI {
    override fun getClientInfo(): Observable<ClientInfo> {
        return remoteDataI.getClientInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
interface ClientRepoI{
    fun getClientInfo():Observable<ClientInfo>
}