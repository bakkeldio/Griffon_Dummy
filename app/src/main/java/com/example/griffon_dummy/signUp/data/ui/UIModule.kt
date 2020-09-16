package com.example.griffon_dummy.signUp.data.ui

import org.koin.dsl.module

val data = module {
    factory<ContractView.SignUpPresenter> { (view : ContractView.View) ->
        SignUpPresenter(get(), get(), view)
    }
    factory<ContractView2.Presenter> { (view: ContractView2.View) ->LastStepPresenter(get(), view) }

    factory<ContractView3.SidPresenter> { (view:  ContractView3.View1) -> SidPresenter(get(), view, get())}
}