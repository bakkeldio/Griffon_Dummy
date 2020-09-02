package com.example.griffon_dummy.modules

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.griffon_dummy.*
import com.example.griffon_dummy.signIn.data.data.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule  = module {
    factory{
        RemoteDataSource(get()) as RemoteDataI
    }
    factory { ClientRepository(get(), get()) as ClientRepoI
    }
    single{ generateStudentsService(get()) }
    single { postSignUpDetails(get()) }
    factory { LocalDataSource(get()) }
    single { createSP(androidContext()) }
    single {  getHttpClient()}


}

private fun createSP(context: Context) : SharedPreferences{
    return context.getSharedPreferences("TestSP", MODE_PRIVATE)
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

private fun postSignUpDetails(client: OkHttpClient) : SignUpService{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://griffon.dar-dev.zone")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    return retrofit.create(SignUpService::class.java)
}

private fun getHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .build()
}