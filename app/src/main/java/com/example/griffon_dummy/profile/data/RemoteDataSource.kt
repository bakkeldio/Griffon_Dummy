package com.example.griffon_dummy.profile.data

import com.example.griffon_dummy.profile.entity.Avatar
import com.example.griffon_dummy.profile.entity.RevokeAccessTokenBody
import com.example.griffon_dummy.profile.entity.UserInfo
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody

class RemoteDataSource(private val api :ProfileInterface) :RemoteDataI{
    override fun uploadImage(data: MultipartBody.Part, token:String): Observable<Avatar> {
        return api.setAvatar(token, data)
    }

    override fun getUserInfo(token: String): Observable<UserInfo> {
        return api.getUserInfo(token)
    }

    override fun revokeAccessToken(token: String): Completable {
        return api.revokeAccessToken(RevokeAccessTokenBody(token))
    }


}
interface RemoteDataI{
    fun uploadImage(data : MultipartBody.Part, token: String): Observable<Avatar>

    fun getUserInfo(token : String): Observable<UserInfo>
    fun revokeAccessToken(token  : String) : Completable
}