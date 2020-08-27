package com.example.griffon_dummy

import com.example.griffon_dummy.dataClasses.ClientInfo
import io.reactivex.Observable


class RemoteDataSource(private val apiService: ApiService) : RemoteDataI{
    override fun getClientInfo(): Observable<ClientInfo> {
        return apiService.getClientInfo("5ea469f9-a517-427b-8b16-290e9e57bb7f",
            "1rg3Y9cseLsyqolScMQ1kYD8flnLYtqFFkQc36D2WPDdnkXs1BqLuq83yDhkfjtH")
    }

}
interface RemoteDataI{
    fun getClientInfo() : Observable<ClientInfo>
}
