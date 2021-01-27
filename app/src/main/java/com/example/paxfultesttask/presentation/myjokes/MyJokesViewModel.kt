package com.example.paxfultesttask.presentation.myjokes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.SingleLiveEvent
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

    private val _needToShowDialog = SingleLiveEvent<Unit>()
    val needToShowDialog = _needToShowDialog.asImmutable()

    init {
        initMyJokesData()
    }

    private fun initMyJokesData() {
        viewModelScope.launch(IO) {
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }

    fun showDialog() {
        _needToShowDialog.postCall()
    }

    fun saveMyJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.likeJoke(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }

    fun dislikeJoke(myJoke: Joke) {
        viewModelScope.launch(IO) {
            interactor.dislikeJokeInteractor(myJoke)
            _myJokesLiveData.postValue(interactor.getAllLikedJokes())
        }
    }
}