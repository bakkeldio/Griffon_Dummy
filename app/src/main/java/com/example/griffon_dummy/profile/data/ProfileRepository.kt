package com.example.griffon_dummy.profile.data

import com.example.griffon_dummy.profile.entity.Avatar
import com.example.griffon_dummy.profile.entity.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class ProfileRepository(private val remoteDataI: RemoteDataI) : ProfileRepositoryI{
    override fun uploadImage(token: String, data: MultipartBody.Part): Observable<Avatar> {
        return remoteDataI.uploadImage(data, token).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUserInfo(token: String): Observable<UserInfo> {
        return remoteDataI.getUserInfo(token).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun revokeAccessToken(token: String): Completable {
        return remoteDataI.revokeAccessToken(token).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
interface ProfileRepositoryI{
    fun uploadImage(token : String, data : MultipartBody.Part): Observable<Avatar>
    fun getUserInfo(token: String):Observable<UserInfo>
    fun revokeAccessToken(token: String) : Completable
}