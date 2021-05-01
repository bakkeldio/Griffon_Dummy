package com.example.griffon_dummy.signIn.data.ui


import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val module = module {
    factory { CompositeDisposable() }

    factory<ClientContract.Presenter> { (view : ClientContract.View) ->
        Presenter(get(), get(), view)
    }

}