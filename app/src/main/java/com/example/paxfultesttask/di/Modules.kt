package com.example.paxfultesttask.di

import com.example.paxfultesttask.data.repositories.*
import com.example.paxfultesttask.domain.api.IcndbApiFactory
import com.example.paxfultesttask.domain.database.JokesDatabase
import com.example.paxfultesttask.domain.interactors.api.ApiInteractor
import com.example.paxfultesttask.domain.interactors.api.IApiInteractor
import com.example.paxfultesttask.domain.interactors.db.IJokeDbInteractor
import com.example.paxfultesttask.domain.interactors.db.JokeDbInteractor
import com.example.paxfultesttask.presentation.jokeslist.JokesListViewModel
import com.example.paxfultesttask.presentation.myjokes.MyJokesViewModel
import com.example.paxfultesttask.presentation.settings.SettingsViewModel
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

    single<IMyJokesDbRepository> {
        MyJokesDbRepository(androidContext())
    }

    single<IJokeDbInteractor> {
        JokeDbInteractor(get(),get(),get())
    }

    single<IApiInteractor> {
        ApiInteractor(get())
    }

    single {
        JokesDatabase.getInstance(get())
    }


    viewModel { JokesListViewModel(get(),get()) }
    viewModel { MyJokesViewModel(get()) }
    viewModel { SettingsViewModel(get()) }

}