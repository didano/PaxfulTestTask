package com.example.paxfultesttask.presentation.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.domain.JokesPreferences
import com.example.paxfultesttask.framework.db.JokesDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModel { SettingsViewModel(get()) }
}

class SettingsViewModel(val context: Context) : ViewModel() {

    val db = JokesDatabase.getInstance(context)

    val jokePrefs = MutableLiveData<JokesPreferences>()

    fun changePref(pref: JokesPreferences){
        viewModelScope.launch(IO) {
            db.myPrefsDao().changePref(pref)
            val result = db.myPrefsDao().getPrefs()
            viewModelScope.launch(Main) {
                if(result!=null){
                    jokePrefs.value = result
                } else {
                    jokePrefs.value = JokesPreferences()
                }
            }
        }
    }

    fun initPref() {
        viewModelScope.launch(IO) {
            val result = db.myPrefsDao().getPrefs()
            viewModelScope.launch(Main) {
                if(result!=null){
                    jokePrefs.value = result
                } else {
                    jokePrefs.value = JokesPreferences()
                }
            }
        }
    }

}