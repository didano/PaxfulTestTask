package com.example.paxfultesttask.presentation.jokeslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.interactors.api.IApiInteractor
import com.example.paxfultesttask.domain.interactors.db.IJokeDbInteractor
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class JokesListViewModel(
    val dbInteractor: IJokeDbInteractor,
    val apiInteractor: IApiInteractor
) : ViewModel() {

    val jokeTextLiveData = MutableLiveData<List<Joke>>()
    val isRefreshed = MutableLiveData<Boolean>()
    var needToRefresh = false

    fun checkOfflineMode() {
        viewModelScope.launch(IO) {
            isRefreshed.postValue(false)
            val result = dbInteractor.getPreferences()
            if (result.offlineMode == 0) {
                needToRefresh = true
                val response = apiInteractor.getAllNamedJokes(result.firstName, result.lastName)
                for (element in response) {
                    dbInteractor.addJoke(element)
                }
                jokeTextLiveData.postValue(response)
            } else {
                jokeTextLiveData.postValue(dbInteractor.getAllJokes())
                needToRefresh = false
            }
            isRefreshed.postValue(true)
        }
    }

    fun getRandomJoke() {
        viewModelScope.launch(IO) {
            isRefreshed.postValue(false)
            val result = dbInteractor.getRandomJoke()
            jokeTextLiveData.postValue(result)
            isRefreshed.postValue(true)
        }
    }

    fun likeJoke(joke: Joke) {
        viewModelScope.launch(IO) {
            dbInteractor.likeJoke(joke)
        }
    }
}