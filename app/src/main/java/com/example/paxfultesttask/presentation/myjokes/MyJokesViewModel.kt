package com.example.paxfultesttask.presentation.myjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.interactors.IJokesInteractor
import com.example.paxfultesttask.utils.asImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MyJokesViewModel(
    private val interactor: IJokesInteractor
) : ViewModel() {

    private val _myJokesLiveData = MutableLiveData<List<Joke>>()
    val myJokesLiveData = _myJokesLiveData.asImmutable()

    init {
        initMyJokesData()
    }

    private fun initMyJokesData() {
        viewModelScope.launch(IO) {
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }

    fun saveMyJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.likeJoke(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }

    fun deleteMyJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.deleteJokeFromLiked(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }
}