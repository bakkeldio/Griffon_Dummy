package com.example.griffon_dummy.presenter

import com.example.griffon_dummy.signIn.data.data.ClientRepoI
import io.reactivex.disposables.CompositeDisposable

class SignUpPresenter(
    private val repository: ClientRepoI,
    private val view : ContractView.View, private val compositeDisposable: CompositeDisposable)  : ContractView.SignUpPresenter{
    override fun getSignUpResult(){
       val result =  repository.getClientInfo().subscribe({
            view.updateImage(it.logoImage!!)
            view.termsConditions(it.termsConditionUrl!!)
        },
            {
                it.printStackTrace()
            })

        compositeDisposable.add(result)
    }



}
interface ContractView{
    interface SignUpPresenter{
        fun getSignUpResult()
    }
    interface View{
        fun updateImage(imageUrl : String)
        fun termsConditions(url: String)
    }
}
