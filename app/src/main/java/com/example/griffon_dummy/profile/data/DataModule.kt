package com.example.griffon_dummy.profile.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val profile_module = module {
        factory<RemoteDataI> { RemoteDataSource(get())}
        factory { generateProfileService(get()) }
        factory<ProfileRepositoryI> { ProfileRepository(get()) }

}

private fun generateProfileService(client: OkHttpClient) : ProfileInterface {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://griffon.dar-dev.zone")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    return retrofit.create(ProfileInterface::class.java)
}