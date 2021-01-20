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

    fun changePref(pref: JokesPreferences) {
        viewModelScope.launch(IO) {
            dbInteractor.writePreferences(pref)
            val result = dbInteractor.getPreferences()
            jokePrefs.postValue(result)
        }
    }

    fun initPref() {
        viewModelScope.launch(IO) {
            val result = dbInteractor.getPreferences()
            jokePrefs.postValue(result)
        }
    }

}