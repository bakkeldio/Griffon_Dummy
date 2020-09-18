package com.example.griffon_dummy.signUp.data.ui

import android.graphics.Color
import com.example.griffon_dummy.signUp.data.data.SignUpRepositoryI
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import com.example.griffon_dummy.signUp.data.entity.PhoneRegister
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LastStepPresenter(
    private val repositoryI: SignUpRepositoryI,
    var view: ContractView2.View?) : ContractView2.Presenter  {
    override fun getClientInfo(){
        val result = repositoryI.getClientInfo()
        view?.updateImage(result.logoImage!!)
        view?.updateButton(result.buttonColor?.colors?.filter {
            it.isNotEmpty()
        }?.map {
            Color.parseColor(it)
        }!!.toIntArray())
    }



    override fun getAccessToken(username: String, password: String) {
        val disposable = repositoryI.getAccessToken(username, password)
            .subscribeWith(object : Observer<AccessToken> {
                override fun onComplete() {
                    view!!.getAccessTokenForEmail()
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: AccessToken) {
                    println(t)
                }

                override fun onError(e: Throwable) {
                    println(e.printStackTrace())
                }

            })
    }


    override fun onDestroy() {
        view = null
    }

    override fun getAccessTokenWithPhone(sid: String, password: String) {
        val accessToken = repositoryI.register(sid, password)
            .subscribe({
                view?.getAccessToken(it.accessToken)
            },
                {
                    it.printStackTrace()
                })
    }

}
interface ContractView2{
    interface View{
        fun updateButton(intArray: IntArray)
        fun updateImage(url : String)
        fun getAccessToken(accessToken : String)
        fun getAccessTokenForEmail()
    }
    interface Presenter{
        fun getClientInfo()
        fun getAccessToken(username : String, password :String)
        fun onDestroy()
        fun getAccessTokenWithPhone(sid: String, password: String)
    }
}