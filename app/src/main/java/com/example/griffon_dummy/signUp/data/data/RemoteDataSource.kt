package com.example.griffon_dummy.signUp.data.data

import com.example.griffon_dummy.signIn.data.data.ApiService
import com.example.griffon_dummy.signUp.data.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

private const val Client_ID = "5ea469f9-a517-427b-8b16-290e9e57bb7f"
private const val Secret = "1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH"
class RemoteDataSource(private val api: SignUpService) : RemoteDataI {
    override fun getAccessToken(username: String, password: String): Observable<AccessToken> {
        return api.signUp(NewUserBody(Client_ID, Secret, username, password))
    }

    override fun getSID(phoneNumber: String) : Observable<PhoneRegister>{
        return api.signUpWithPhone( NewUserBody(Client_ID, Secret, phoneNumber,null))
    }

    override fun verifyPhone(sid: String, code: String): Observable<PhoneResponse> {
        return api.phoneVerify(sid, SmsCodeRequestBody(code))
    }

    override fun register(password: String, sid: String): Observable<AccessToken> {
        return api.registerWithPassword(sid, PasswordRequestBody(password))
    }


}
interface RemoteDataI{
    fun getAccessToken(username: String, password: String): Observable<AccessToken>
    fun getSID(phoneNumber: String) : Observable<PhoneRegister>
    fun verifyPhone(sid : String, code : String) : Observable<PhoneResponse>
    fun register(password : String, sid : String): Observable<AccessToken>
}