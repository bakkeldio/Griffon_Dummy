package com.example.griffon_dummy.resetPassword.ui

import android.graphics.Color
import com.example.griffon_dummy.resetPassword.data.LocalDataI
import com.example.griffon_dummy.resetPassword.data.RemoteDataI
import com.example.griffon_dummy.resetPassword.data.ResetRepositoryI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConfirmOtpPresenter(
    private val remoteRepositoryI:  ResetRepositoryI,
    private val localDataI: LocalDataI,
    var view: ContractOtp.View1?,
    private val compositeDisposable: CompositeDisposable) : ContractOtp.Presenter{
    override fun getDesign() {
        val results = localDataI.getSP()
        view?.updateButton(results.buttonColor!!.colors.filter {
            it.isNotEmpty()
        }.map {
            Color.parseColor(it)
        }.toIntArray())
        view?.updateImage(results.logoImage!!)
    }

    override fun putCode(sid: String,code: String) {
        val disposable = remoteRepositoryI.getConfirmation(sid, code)
            .subscribe({
            view?.getSid1(it.sid)
        },{
            it.printStackTrace()
        })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }

    override fun resendOtp(username: String,resent_option: String) {
        val results = remoteRepositoryI.getOtpForUsername(username,resent_option).subscribe({
            view?.getSid(it.sid)
        },{
            it.printStackTrace()
        })
        compositeDisposable.add(results)
    }

}
interface ContractOtp{
    interface Presenter{
        fun getDesign()
        fun putCode(sid: String, code: String)
        fun onDestroy()
        fun resendOtp(username: String,resent_option : String)
    }
    interface View1{
        fun updateButton(colors : IntArray)
        fun updateImage(url: String)
        fun getSid(sid : String)
        fun getSid1(message : String)
    }
}