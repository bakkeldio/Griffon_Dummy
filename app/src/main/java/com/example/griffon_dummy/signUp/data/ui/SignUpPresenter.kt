package com.example.griffon_dummy.signUp.data.ui


import android.graphics.Color
import com.example.griffon_dummy.signUp.data.data.SignUpRepositoryI
import com.example.griffon_dummy.signUp.data.entity.PhoneRegister
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignUpPresenter(
    private val compositeDisposable: CompositeDisposable,
    private val repository: SignUpRepositoryI,
    private var view : ContractView.View?)  :
    ContractView.SignUpPresenter {
    override fun getSignUpResult(){
       val result =  repository.getClientInfo()
        view?.updateImage(result.logoImage!!)
        view?.termsConditions(result.termsConditionUrl!!)
        view?.updateButton(result.buttonColor!!.colors.filter {
            it.isNotEmpty()
        }.map {
            Color.parseColor(it)
        }.toIntArray())
    }

    override fun giveNumber(phoneNumber: String) {
        val sid = repository
            .getSID(phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.takeSidDuration(it.sid, it.add_info)
            },
                {
                    it.printStackTrace()
                })

        compositeDisposable.add(sid)
    }


    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }
}
interface ContractView{
    interface SignUpPresenter{
        fun getSignUpResult()
        fun giveNumber(phoneNumber: String)
        fun onDestroy()
    }
    interface View{
        fun updateImage(imageUrl : String)
        fun termsConditions(url: String)
        fun updateButton(colors : IntArray)
        fun takeSidDuration(sid : String, time: String)

    }
}
