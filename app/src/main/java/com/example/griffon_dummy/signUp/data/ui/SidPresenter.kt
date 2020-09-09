package com.example.griffon_dummy.signUp.data.ui

import android.graphics.Color
import com.example.griffon_dummy.signIn.data.data.LocalDataI
import com.example.griffon_dummy.signUp.data.data.RemoteDataI
import com.example.griffon_dummy.signUp.data.data.SignUpLocal
import com.example.griffon_dummy.signUp.data.data.SignUpLocalI
import com.example.griffon_dummy.signUp.data.data.SignUpRepositoryI

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SidPresenter(private val repositoryI: SignUpRepositoryI, var view: ContractView3.View1?)
    : ContractView3.SidPresenter{
    override fun getCompletable(sid: String, code:String) {
        val verify = repositoryI
            .verifyPhone(sid, code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.takeSid(it.sid)
            },{
                it.printStackTrace()
            })
    }

    override fun getDesign() {
       val design =  repositoryI.getClientInfo()
        view?.update(design.buttonColor!!.colors.filter {
            it.isNotEmpty()
        }.map {
            Color.parseColor(it)
        }.toIntArray())

        view?.logo(design.logoImage!!)
    }

    override fun onDestroy() {
        view = null
    }
}
interface ContractView3{
    interface SidPresenter{
        fun getCompletable(sid : String, code :String)
        fun getDesign()
        fun onDestroy()
    }
    interface View1{
        fun takeSid(sid : String)
        fun update(colors : IntArray)
        fun logo(url : String)
    }
}