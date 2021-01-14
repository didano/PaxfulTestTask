package com.example.paxfultesttask.presentation.jokeslist

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.domain.Joke
import com.example.paxfultesttask.core.domain.JokesPreferences
import com.example.paxfultesttask.framework.api.JokesRepository
import com.example.paxfultesttask.framework.db.JokesDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val jokesListViewModelModule = module {
    viewModel {
        JokesListViewModel(get())
    }
}

class JokesListViewModel(val context: Context) : ViewModel() {

    val jokeTextLiveData = MutableLiveData<List<Joke>>()

    val jokesPreferences = MutableLiveData<JokesPreferences>()

    val db = JokesDatabase.getInstance(context)

    fun fillLiveData() {
        viewModelScope.launch(IO) {
            val result = JokesRepository.getJokes()
            for (element in result) {
                db.jokesDao().addJoke(element)
            }
            viewModelScope.launch(Main) {
                jokeTextLiveData.value = result
            }
        }
    }

    fun fillLiveDataWithNamedJokes(name: String = "Chuck", lastname: String = "Norris") {
        viewModelScope.launch(IO) {
            val result = JokesRepository.getJokesBySurnameAndName(name, lastname)
            for (element in result) {
                db.jokesDao().addJoke(element)
            }
            viewModelScope.launch(Main) {
                jokeTextLiveData.value = result
            }
        }
    }

    fun fillLiveDataOffline() {
        viewModelScope.launch(IO) {
            val result = db.jokesDao().getAllJokes()
            viewModelScope.launch(Main) {
                jokeTextLiveData.value = result
            }
        }
    }

    fun checkOfflineMode() {
        viewModelScope.launch(IO) {
            val result = db.myPrefsDao().getPrefs()
            viewModelScope.launch(Main) {
                if (result != null) {
                    jokesPreferences.value = result
                } else {
                    jokesPreferences.value = JokesPreferences()
                }
            }
        }
    }

    fun getRandomJoke() {
        viewModelScope.launch(IO) {
            val result = db.jokesDao().getRandomJoke()
            viewModelScope.launch(Main) {
                jokeTextLiveData.value = result
            }
        }
    }

    fun likeJoke(joke: Joke) {
        viewModelScope.launch(IO) {
            db.myJokesDao().addJokeToLiked(joke.getMyJoke())
        }
    }

}