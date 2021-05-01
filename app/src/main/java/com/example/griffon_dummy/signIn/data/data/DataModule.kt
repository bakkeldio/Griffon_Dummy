package com.example.griffon_dummy.signIn.data.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.example.griffon_dummy.signUp.data.data.SignUpService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    single{ generateStudentsService(get())
    }
    factory { LocalDataSource(get()) as LocalDataI}
    single { createSP(androidContext()) }
    factory { loggingInterceptor() }
    single { getHttpClient(get())}


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

private fun loggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger{
        override fun log(message: String) {
            Log.d("OkHttp", message)
        }

    }).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}


private fun getHttpClient(httpInterceptor: HttpLoggingInterceptor) : OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(httpInterceptor)
        .build()
}