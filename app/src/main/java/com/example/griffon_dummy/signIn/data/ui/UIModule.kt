package com.example.griffon_dummy

import com.example.griffon_dummy.signIn.data.ui.ClientContract
import com.example.griffon_dummy.presenter.ContractView
import com.example.griffon_dummy.signIn.data.ui.Presenter
import com.example.griffon_dummy.presenter.SignUpPresenter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val module = module {
    factory { CompositeDisposable() }

    factory<ClientContract.Presenter> { (view : ClientContract.View) ->
        Presenter(
            get(),
            get(),
            view
        )
    }
    factory<ContractView.SignUpPresenter> { (view : ContractView.View) ->SignUpPresenter(get(), view, get())}
}