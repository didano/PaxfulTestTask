package com.example.paxfultesttask.presentation.jokeslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.SingleLiveEvent
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.interactors.IJokesInteractor
import com.example.paxfultesttask.utils.asImmutable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class JokesListViewModel(
    private val interactor: IJokesInteractor
) : ViewModel() {

    private val _jokeTextMutable = MutableLiveData<List<Joke>>()
    val jokeTextMutable = _jokeTextMutable.asImmutable()

    private val _jokeLiked = SingleLiveEvent<Unit>()
    val jokeLiked = _jokeLiked.asImmutable()

    private val _isRefreshed = MutableLiveData<Boolean>()
    val isRefreshed = _isRefreshed.asImmutable()

    private val _changeFragmentTitle = MutableLiveData<String>()
    val changeFragmentTitle = _changeFragmentTitle.asImmutable()

    private val _showErrorToast = SingleLiveEvent<String>()
    val showErrorToast = _showErrorToast.asImmutable()

    init {
        initData()
    }

    fun initData() {
        viewModelScope.launch(IO) {
            _isRefreshed.postValue(false)
            checkForErrorOrFillList()
            _isRefreshed.postValue(true)
        }
    }

    fun likeJoke(joke: Joke) {
        viewModelScope.launch(IO) {
            interactor.likeJoke(joke)
            _jokeLiked.postCall()
        }
    }

    fun onShakeAction() {
        viewModelScope.launch(IO) {
            _isRefreshed.postValue(false)
            if (interactor.getOfflineMode()) {
                _jokeTextMutable.postValue(interactor.getRandomJoke())
            } else {
                checkForErrorOrFillList()
            }
            _isRefreshed.postValue(true)
        }
    }

    private suspend fun checkForErrorOrFillList(){
        val result = interactor.getAllJokesApi()
        val errorText:String? = result.last().errorText
        if (!errorText.isNullOrBlank()) {
            _showErrorToast.postValue(errorText)
            _jokeTextMutable.postValue(interactor.getAllJokesFromDb())
        } else {
            _jokeTextMutable.postValue(result)
        }
    }

    fun changeTitle(title: String) {
        _changeFragmentTitle.postValue(title)
    }
}