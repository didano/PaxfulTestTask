package com.example.paxfultesttask.presentation.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.domain.interactors.IJokesInteractor
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
            _jokePrefsFirstName.postValue(interactor.getFirstName() ?: "")
            _jokePrefsLastName.postValue(interactor.getLastName() ?: "")
            _isOffline.postValue(interactor.getOfflineMode())
        }
    }

    fun updatePrefFirstName(firstName: String) {
        viewModelScope.launch(IO) {
            interactor.writeFirstName(firstName)
        }
    }

    fun updatePrefLastName(lastName: String) {
        viewModelScope.launch(IO) {
            interactor.writeLastName(lastName)
        }
    }

    fun updateOfflineMode(boolean: Boolean) {
        viewModelScope.launch(IO) {
            interactor.writeOfflineMode(boolean)
        }
    }
}