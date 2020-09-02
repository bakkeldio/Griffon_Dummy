package com.example.griffon_dummy.presenter

import android.graphics.Color
import com.example.griffon_dummy.signIn.data.data.ClientRepoI
import io.reactivex.disposables.CompositeDisposable

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