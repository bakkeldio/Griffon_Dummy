package com.example.griffon_dummy.profile.ui

import org.koin.dsl.module

val profileUI_module = module {
    factory<ProfileContract.ProfilePresenter> { (view: ProfileContract.View)->ProfilePresenter(get(), get(), view) }
}