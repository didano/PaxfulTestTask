package com.example.paxfultesttask.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.domain.interactors.db.IJokeDbInteractor
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class SettingsViewModel(val dbInteractor: IJokeDbInteractor) : ViewModel() {

    val jokePrefs = MutableLiveData<JokesPreferences>()

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(IO) {
            jokePrefs.postValue(dbInteractor.getPreferences())
        }
    }


    fun changePref(pref: JokesPreferences) {
        viewModelScope.launch(IO) {
            dbInteractor.writePreferences(pref)
        }
    }
}