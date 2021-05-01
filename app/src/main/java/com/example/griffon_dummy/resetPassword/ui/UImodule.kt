package com.example.griffon_dummy.resetPassword.ui

import org.koin.dsl.module

val ui_module = module {
    factory<ContractView.Presenter> {  (view: ContractView.View1) -> PasswordPresenter(get(), get(), view)}
    factory <ContractOtp.Presenter>{ (view : ContractOtp.View1)->ConfirmOtpPresenter(get(), get(), view, get())}
    factory <Contract.Presenter>{(view: Contract.View) -> Presenter(get(), get(), view, get()) }
}