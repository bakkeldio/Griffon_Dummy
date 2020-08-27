package com.example.griffon_dummy.modules

import com.example.griffon_dummy.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule  = module {
    factory{
        RemoteDataSource(get()) as RemoteDataI
    }
    factory { ClientRepository(get()) as ClientRepoI}
    single{ generateStudentsService(get()) }

    single {  getHttpClient()}


}
private fun generateStudentsService(client: OkHttpClient) : ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://griffon.dar-dev.zone")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    return retrofit.create(ApiService::class.java)
}

private fun getHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}