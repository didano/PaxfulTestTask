package com.example.paxfultesttask

import android.app.Application
import com.example.paxfultesttask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PaxfulApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PaxfulApp)
            androidLogger()
            modules(appModule)
        }
    }
}