package com.example.paxfultesttask.presentation.myjokes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.interactors.db.IJokesInteractor
import com.example.paxfultesttask.utils.asImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MyJokesViewModel(val interactor: IJokesInteractor) : ViewModel() {
    private val _myJokesLiveData = MutableLiveData<List<Joke>>()
    val myJokesLiveData = _myJokesLiveData.asImmutable()

    init {
        fillMyJokesData()
    }

    fun fillMyJokesData() {
        viewModelScope.launch(IO) {
            Log.d("MYJOKESINIT",interactor.getAllLikedJokes().toString())
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }

    fun saveMyJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.likeJoke(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
            Log.d("MYJOKES",interactor.getAllLikedJokes().toString())
        }
    }

    fun deleteMyJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.deleteJokeFromLiked(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }
}