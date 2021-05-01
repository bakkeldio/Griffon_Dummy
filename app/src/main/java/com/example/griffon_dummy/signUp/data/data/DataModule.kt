package com.example.griffon_dummy.signUp.data.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val signUpData = module {

    factory<SignUpLocalI> { SignUpLocal(get()) }
    factory<SignUpRepositoryI> { SignUpRepository(get(), get())}
    factory<RemoteDataI> {RemoteDataSource(get())}
    single{ generateSignUpService(get())}
}

private fun generateSignUpService(client: OkHttpClient) : SignUpService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://griffon.dar-dev.zone")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    return retrofit.create(SignUpService::class.java)
}
