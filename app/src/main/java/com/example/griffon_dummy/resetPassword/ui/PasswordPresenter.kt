package com.example.griffon_dummy.resetPassword.ui

import android.graphics.Color
import com.example.griffon_dummy.resetPassword.data.ResetRepositoryI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PasswordPresenter(
    private val resetRepositoryI: ResetRepositoryI,
    private val compositeDisposable: CompositeDisposable,
    var view: ContractView.View1?
) : ContractView.Presenter{
    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }



    override fun getOtpForUsername(username: String) {
        val result = resetRepositoryI.getOtpForUsername(username)
            .subscribe({
                view?.getMessage(it.add_info, it.sid)
            },{
                it.printStackTrace()
            })

        compositeDisposable.add(result)
    }

    override fun getSP() {
        val sp = resetRepositoryI.getSP()
        sp.buttonColor?.colors?.filter {
            it.isNotEmpty()
        }?.map {
            Color.parseColor(it)
        }?.toIntArray()?.let { view?.updateButton(it) }
        sp.logoImage?.let { view?.updateImage(it) }

    }
}

interface ContractView{
    interface Presenter{
        fun onDestroy()
        fun getOtpForUsername(username: String)
        fun getSP()
    }
    interface View1{
        fun getMessage(addInfo : String, sid: String)
        fun updateImage(url: String)
        fun updateButton(colors: IntArray)
    }
}