package com.example.paxfultesttask.di

import com.example.paxfultesttask.data.repositories.*
import com.example.paxfultesttask.domain.api.IcndbApiFactory
import com.example.paxfultesttask.domain.database.JokesDatabase
import com.example.paxfultesttask.domain.interactors.db.IJokesInteractor
import com.example.paxfultesttask.domain.interactors.db.JokesInteractor
import com.example.paxfultesttask.presentation.jokeslist.JokesListViewModel
import com.example.paxfultesttask.presentation.myjokes.MyJokesViewModel
import com.example.paxfultesttask.presentation.settings.SettingsViewModel
import com.example.paxfultesttask.utils.ShakeDetectionUtil
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        IcndbApiFactory.getInstance()
    }

    single<IApiRepository>{
        ApiRepository(get())
    }

    single<IPrefsRepository> {
        PrefsRepository(androidContext())
    }

    single<IJokesDbRepository> {
        JokesDbRepository(androidContext())
    }

    factory {
        ShakeDetectionUtil(get())
    }

    single<IJokesInteractor>{
        JokesInteractor(get(),get(),get())
    }

    single {
        JokesDatabase.getInstance(get())
    }


    viewModel { JokesListViewModel(get()) }
    viewModel { MyJokesViewModel(get()) }
    viewModel { SettingsViewModel(get()) }

}