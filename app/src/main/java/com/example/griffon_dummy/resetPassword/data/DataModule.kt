package com.example.griffon_dummy.resetPassword.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val reset_module = module {
    factory<RemoteDataI> {RemoteDataSource(get())}
    factory<LocalDataI>{  LocalDataSource(get())}
    single { generatePasswordResetService(get()) }
    factory<ResetRepositoryI> { ResetRepository(get(), get())}


}

private fun generatePasswordResetService(client: OkHttpClient) :PasswordResetService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://griffon.dar-dev.zone")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    return retrofit.create(PasswordResetService::class.java)
}
