package com.example.griffon_dummy

import android.app.Application
import com.example.griffon_dummy.modules.dataModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module, dataModule)
        }
    }
}