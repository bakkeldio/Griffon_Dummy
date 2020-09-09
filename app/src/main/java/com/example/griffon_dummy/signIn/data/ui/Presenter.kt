package com.example.griffon_dummy.signIn.data.ui

import android.graphics.Color
import com.example.griffon_dummy.signIn.data.data.ClientRepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val repository: ClientRepoI,
    private val compositeDisposable : CompositeDisposable,
    private var view: ClientContract.View?
) : ClientContract.Presenter {
    override fun getClientInfo() {

        val clients = repository.getClientInfo()
            .subscribe({ clientInfo ->

                view?.updateBackground(clientInfo.backgroundColor!!)
                view?.loadLogo(clientInfo.logoImage!!)
                view?.updateButton(clientInfo.buttonColor?.colors?.filter {
                    it.isNotEmpty()
                }?.map {
                    Color.parseColor(it)
                }!!.toIntArray())
            },{
                it.printStackTrace()
            })

        compositeDisposable.add(clients)
    }

    override fun getAccessToken(username: String, password: String) {
        val token = repository.getAccessToken(username, password).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.successfullySignIn()
            },{
                it.printStackTrace()
            })
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        view = null
    }


}
interface ClientContract {
    interface View {
        fun updateButton(colors : IntArray)
        fun loadLogo(url : String)
        fun updateBackground(color: String)
        fun successfullySignIn()
    }

    interface Presenter{
        fun getClientInfo()
        fun getAccessToken(username : String, password: String)
        fun onDestroy()
    }
}