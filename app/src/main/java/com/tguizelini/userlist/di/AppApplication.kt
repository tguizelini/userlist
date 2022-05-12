package com.tguizelini.userlist.di

import android.app.Application
import com.tguizelini.userlist.di.modules.UserModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(UserModule)
        }
    }
}