package com.example.paxfultesttask.presentation.jokeslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.domain.interactors.api.IApiInteractor
import com.example.paxfultesttask.domain.interactors.db.IJokeDbInteractor
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class JokesListViewModel(
    val dbInteractor: IJokeDbInteractor,
    val apiInteractor: IApiInteractor
) : ViewModel() {

    val jokeTextLiveData = MutableLiveData<List<Joke>>()
    val jokesPreferences = MutableLiveData<JokesPreferences>()

    fun fillLiveData() {
        viewModelScope.launch(IO) {
            val result = apiInteractor.getAllJokes()
            for (element in result) {
                dbInteractor.addJoke(element)
            }
            jokeTextLiveData.postValue(result)
        }
    }

    fun fillLiveDataWithNamedJokes(name: String = "Chuck", lastname: String = "Norris") {
        val value = jokesPreferences.value
        viewModelScope.launch(IO) {
            val result = apiInteractor.getAllNamedJokes(
                value?.firstName ?: name,
                value?.lastName ?: lastname
            )
            for (element in result) {
                dbInteractor.addJoke(element)
            }
            jokeTextLiveData.postValue(result)
        }
    }

    fun fillLiveDataOffline() {
        viewModelScope.launch(IO) {
            val result = dbInteractor.getAllJokes()
            jokeTextLiveData.postValue(result)
        }
    }

    fun checkOfflineMode() {
        viewModelScope.launch(IO) {
            val result = dbInteractor.getPreferences()
            jokesPreferences.postValue(result)
        }
    }

    fun getRandomJoke() {
        viewModelScope.launch(IO) {
            val result = dbInteractor.getRandomJoke()
            jokeTextLiveData.postValue(result)
        }
    }

    fun likeJoke(joke: Joke) {
        viewModelScope.launch(IO) {
            dbInteractor.likeJoke(joke)
        }
    }


}