package com.tguizelini.userlist.di.modules

import com.tguizelini.userlist.data.UserRepository
import com.tguizelini.userlist.data.UserRepositoryImpl
import com.tguizelini.userlist.data.UserService
import com.tguizelini.userlist.domain.UserInteractor
import com.tguizelini.userlist.domain.UserInteractorImpl
import com.tguizelini.userlist.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val UserModule = module {
    factory{ UserService() }
    factory<UserRepository>{ UserRepositoryImpl(get()) }
    factory<UserInteractor>{ UserInteractorImpl(get()) }

    viewModel { UserViewModel(get()) }
}