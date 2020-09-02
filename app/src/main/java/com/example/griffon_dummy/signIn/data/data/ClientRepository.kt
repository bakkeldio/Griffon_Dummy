package com.example.griffon_dummy

import com.example.griffon_dummy.signIn.data.ClientInfo
import com.example.griffon_dummy.signIn.LocalDataI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ClientRepository(private val remoteDataI: RemoteDataI,
                       private val localDataI: LocalDataI
) : ClientRepoI {
    override fun getClientInfo(): Observable<ClientInfo> {
        return remoteDataI.getClientInfo()
            .map {
                localDataI.saveClient(it)
                it
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
interface ClientRepoI{
    fun getClientInfo():Observable<ClientInfo>
}