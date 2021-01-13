package com.example.paxfultesttask

import android.app.Application
import com.example.paxfultesttask.framework.api.jokesRepositoryModule
import com.example.paxfultesttask.presentation.jokeslist.jokesListFragmentModule
import com.example.paxfultesttask.presentation.jokeslist.jokesListViewModelModule
import com.example.paxfultesttask.presentation.myjokes.myJokesFragmentModule
import com.example.paxfultesttask.presentation.myjokes.myJokesViewModelModule
import com.example.paxfultesttask.presentation.settings.settingsFragmentModule
import com.example.paxfultesttask.presentation.settings.settingsViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PaxfulApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PaxfulApp)
            modules(
                listOf(
                    jokesListFragmentModule, jokesListViewModelModule, jokesRepositoryModule,
                    myJokesFragmentModule, myJokesViewModelModule, settingsFragmentModule,
                    settingsViewModelModule
                )
            )
        }
    }
}