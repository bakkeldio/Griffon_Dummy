package com.example.griffon_dummy.profile.ui

import android.util.Log
import android.widget.Toast
import com.example.griffon_dummy.profile.data.ProfileRepositoryI
import com.example.griffon_dummy.profile.entity.UserInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody

class ProfilePresenter(private val profileRepositoryI: ProfileRepositoryI,
                        private  val compositeDisposable: CompositeDisposable,
                        private var view: ProfileContract.View?): ProfileContract.ProfilePresenter{
    override fun onDestroy() {
        view = null
        compositeDisposable.dispose()
    }

    override fun uploadImage(image: MultipartBody.Part, token: String) {
        val disposable = profileRepositoryI.uploadImage(token, image).subscribe({
            Log.e("TAG", it.original)
            view?.setImage(it.original)
        },{
            it.printStackTrace()
        })
        compositeDisposable.add(disposable)
    }

    override fun getUserInfo(token: String) {
        val disposable = profileRepositoryI.getUserInfo(token).subscribe({
            view?.getUserInfo(it)
        },{
            it.printStackTrace()
        })
        compositeDisposable.add(disposable)
    }

    override fun revokeAccessToken(token: String) {
        val disposable = profileRepositoryI.revokeAccessToken(token).subscribe({
            view?.successfullyRevoked()
        },{
            it.printStackTrace()
        })
        compositeDisposable.add(disposable)
    }
}
interface ProfileContract{
    interface View{
        fun setImage(url: String)
        fun getUserInfo(userInfo: UserInfo)
        fun successfullyRevoked()
    }
    interface ProfilePresenter{
        fun onDestroy()
        fun uploadImage(image: MultipartBody.Part, token: String)
        fun getUserInfo(token: String)
        fun revokeAccessToken(token:String)
    }
}