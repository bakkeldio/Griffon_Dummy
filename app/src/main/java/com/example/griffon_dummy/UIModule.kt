package com.example.griffon_dummy

import com.example.griffon_dummy.presenter.ClientContract
import com.example.griffon_dummy.presenter.Presenter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val module = module {
    factory { CompositeDisposable() }

    factory<ClientContract.Presenter> { (view : ClientContract.View) -> Presenter(get(), get(), view)}
}