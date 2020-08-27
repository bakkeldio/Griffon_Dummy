package com.example.griffon_dummy.presenter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import com.example.griffon_dummy.ClientRepoI
import com.example.griffon_dummy.Dummy
import com.example.griffon_dummy.dataClasses.ClientInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(
    private val repository: ClientRepoI,
    private val compositeDisposable : CompositeDisposable
    , private var view: ClientContract.View?
) : ClientContract.Presenter{
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
    }
    interface Presenter{
        fun getClientInfo()
        fun onDestroy()
    }
}