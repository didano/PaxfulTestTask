package com.example.paxfultesttask.presentation.jokeslist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.SingleLiveEvent
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.interactors.db.IJokesInteractor
import com.example.paxfultesttask.utils.asImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class JokesListViewModel(
    private val interactor: IJokesInteractor
) : ViewModel() {

    private val _jokeTextMutable = MutableLiveData<List<Joke>>()
    val jokeTextMutable = _jokeTextMutable.asImmutable()

    private val _isShakeEnabled = MutableLiveData<Boolean>()
    val isShakeEnabled = _isShakeEnabled.asImmutable()

    private val _jokeLiked = SingleLiveEvent<Unit>()
    val jokeLiked = _jokeLiked.asImmutable()

    private val _isRefreshed = MutableLiveData<Boolean>()
    val isRefreshed = _isRefreshed.asImmutable()

    init {
        initData()
    }

    fun initData(){
        viewModelScope.launch(IO) {
            _isRefreshed.postValue(false)
            val name = interactor.getFirstName()
            val lastName = interactor.getLastName()
            val offlineMode = interactor.getOfflineMode()
            Log.d("PREFS","$name $lastName $offlineMode")
            _isShakeEnabled.postValue(true)
            _jokeTextMutable.postValue(interactor.getAllJokesApi(name,lastName))
            _isRefreshed.postValue(true)
        }
    }

    fun likeJoke(joke: Joke) {
        viewModelScope.launch(IO) {
            interactor.likeJoke(joke)
            _jokeLiked.postCall()
            Log.d("MYJOKESLIST",interactor.getAllLikedJokes().toString())
        }
    }

    fun onShakeAction() {
        viewModelScope.launch(IO) {
            val name = interactor.getFirstName()
            val lastName = interactor.getLastName()
            Log.d("Offline","HERE")
            _isRefreshed.postValue(false)
            if(interactor.getOfflineMode()){
                _jokeTextMutable.postValue(interactor.getRandomJoke())
            } else {
                _jokeTextMutable.postValue(interactor.getAllJokesApi(name,lastName))
            }
            _isRefreshed.postValue(true)
        }
    }
}