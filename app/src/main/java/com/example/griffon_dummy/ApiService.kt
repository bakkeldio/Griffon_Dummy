package com.example.griffon_dummy

import com.example.griffon_dummy.dataClasses.ClientInfo
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/v1/mgmt/client-info")
    fun getClientInfo(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): Observable<ClientInfo>
}