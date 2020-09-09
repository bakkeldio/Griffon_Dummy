package com.example.griffon_dummy.resetPassword.ui

import android.graphics.Color
import com.example.griffon_dummy.resetPassword.data.LocalDataI
import com.example.griffon_dummy.resetPassword.data.RemoteDataI
import com.example.griffon_dummy.resetPassword.data.ResetRepositoryI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class Presenter(private val localDataI: LocalDataI,
                private val remoteRepositoryI: ResetRepositoryI,
                var view: Contract.View?,
                private val compositeDisposable: CompositeDisposable): Contract.Presenter{
    override fun getDesign() {
        val results = localDataI.getSP()
        view?.updateButton(results.buttonColor!!.colors.filter {
            it.isNotEmpty()
        }.map {
            Color.parseColor(it)
        }.toIntArray())
        view?.updateLogo(results.logoImage!!)

    }

    override fun getUpdatePasswordConfirm(sid: String, newPassword: String) {
        val updatePassword =remoteRepositoryI.getUpdatePasswordConfirmation(sid, newPassword)
            .subscribe ({
            view?.getSuccessMessage()
        },{
            it.printStackTrace()
        }
        )
        compositeDisposable.add(updatePassword)
    }

    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }

}
interface Contract{
    interface Presenter{
        fun getDesign()
        fun getUpdatePasswordConfirm(sid: String, newPassword: String)
        fun onDestroy()
    }
    interface View{
        fun updateButton(colors: IntArray)
        fun updateLogo(url: String)
        fun getSuccessMessage()
    }
}