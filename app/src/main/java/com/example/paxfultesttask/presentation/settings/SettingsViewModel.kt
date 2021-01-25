package com.example.paxfultesttask.presentation.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.domain.interactors.db.IJokesInteractor
import com.example.paxfultesttask.utils.asImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class SettingsViewModel(val interactor: IJokesInteractor) : ViewModel() {

    private val _jokePrefsFirstName = MutableLiveData<String>()
    val jokePrefsFirstName = _jokePrefsFirstName.asImmutable()

    private val _jokePrefsLastName = MutableLiveData<String>()
    val jokePrefsLastName = _jokePrefsLastName.asImmutable()

    private val _isOffline = MutableLiveData<Boolean>()
    val isOffline = _isOffline.asImmutable()

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(IO) {
            _jokePrefsFirstName.postValue(interactor.getFirstName())
            _jokePrefsLastName.postValue(interactor.getLastName())
            _isOffline.postValue(interactor.getOfflineMode())
        }
    }

    fun updatePrefFirstName(firstName:String){
        viewModelScope.launch(IO){
            Log.d("NAMEFIRST",interactor.getFirstName())
            interactor.writeFirstName(firstName)
            Log.d("NAMEFIRST",interactor.getFirstName())
        }
    }

    fun updatePrefLastName(lastName:String){
        viewModelScope.launch(IO){
            Log.d("NAMEPRE",interactor.getLastName())
            interactor.writeLastName(lastName)
            Log.d("NAMEAFTER",interactor.getLastName())
        }
    }

    fun updateOfflineMode(boolean: Boolean){
        viewModelScope.launch(IO){
            Log.d("OFFLINEPRE",interactor.getOfflineMode().toString())
            interactor.writeOfflineMode(boolean)
            Log.d("OFFLINEAFTER",interactor.getOfflineMode().toString())
        }
    }
}