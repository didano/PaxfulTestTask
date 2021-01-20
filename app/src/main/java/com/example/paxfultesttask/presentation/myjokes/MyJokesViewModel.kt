package com.example.paxfultesttask.presentation.myjokes

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.data.models.MyJoke
import com.example.paxfultesttask.domain.interactors.db.IJokeDbInteractor
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MyJokesViewModel(val dbInteractor: IJokeDbInteractor) : ViewModel() {
    val myJokesLiveData = MutableLiveData<List<MyJoke>>()

    fun fillMyJokesLiveData() {
        viewModelScope.launch(IO) {
            val result = dbInteractor.getAllMyJokes()
            myJokesLiveData.postValue(result)
        }
    }

    fun saveMyJoke(myJoke: MyJoke) {
        viewModelScope.launch(IO) {
            dbInteractor.addOwnJoke(myJoke)
            myJokesLiveData.postValue(dbInteractor.getAllMyJokes())
        }
    }

    fun deleteMyJoke(myJoke: MyJoke) {
        viewModelScope.launch(IO) {
            dbInteractor.deleteMyJoke(myJoke)
            myJokesLiveData.postValue(dbInteractor.getAllMyJokes())
        }
    }
}