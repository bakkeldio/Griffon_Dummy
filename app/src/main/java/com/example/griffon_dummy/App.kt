package com.example.griffon_dummy

import android.app.Application
import com.example.griffon_dummy.resetPassword.data.reset_module
import com.example.griffon_dummy.resetPassword.ui.ui_module
import com.example.griffon_dummy.signIn.data.data.dataModule
import com.example.griffon_dummy.signIn.data.ui.module
import com.example.griffon_dummy.signUp.data.data.signUpData
import com.example.griffon_dummy.signUp.data.ui.data
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin



class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                module,dataModule,signUpData, data, ui_module, reset_module
            )
        }
    }
}