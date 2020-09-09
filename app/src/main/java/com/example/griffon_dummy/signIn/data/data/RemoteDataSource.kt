package com.example.griffon_dummy.signIn.data.data

import com.example.griffon_dummy.signIn.data.entity.ClientInfo
import com.example.griffon_dummy.signUp.data.entity.AccessToken
import io.reactivex.Observable

const val client_id = "5ea469f9-a517-427b-8b16-290e9e57bb7f"
const val client_secret ="1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH"
class RemoteDataSource(private val apiService: ApiService) :
    RemoteDataI {

    override fun getClientInfo(): Observable<ClientInfo> {
        return apiService.getClientInfo(
            client_id,
            client_secret)
    }

    override fun getAccessToken(username: String, password: String): Observable<AccessToken> {
        return apiService.grantNewAccessToken("password", client_id, client_secret, username, password)
    }

}
interface RemoteDataI{
    fun getClientInfo() : Observable<ClientInfo>
    fun getAccessToken(username : String, password : String) : Observable<AccessToken>
}
