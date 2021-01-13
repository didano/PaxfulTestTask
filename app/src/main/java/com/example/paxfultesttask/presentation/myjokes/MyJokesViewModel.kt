package com.example.paxfultesttask.presentation.myjokes

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paxfultesttask.core.domain.MyJoke
import com.example.paxfultesttask.framework.db.JokesDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myJokesViewModelModule = module {
    viewModel {
        MyJokesViewModel(get())
    }
}

class MyJokesViewModel(val context: Context) : ViewModel() {
    val myJokesLiveData = MutableLiveData<List<MyJoke>>()
    val db = JokesDatabase.getInstance(context)

    fun fillMyJokesLiveData() {
        viewModelScope.launch(IO) {
            val result = db.myJokesDao().getAllLikedJokes()
            viewModelScope.launch(Main) {
                myJokesLiveData.value = result
            }
        }
    }

    fun saveMyJoke(myJoke: MyJoke) {
        viewModelScope.launch(IO) {
            db.myJokesDao().addJokeToLiked(myJoke)
            viewModelScope.launch(Main) {
                myJokesLiveData.value =
                    db.myJokesDao().getAllLikedJokes()
            }
        }
    }

    fun deleteMyJoke(myJoke: MyJoke) {
        viewModelScope.launch(IO) {
            db.myJokesDao().deleteMyJoke(myJoke)
            viewModelScope.launch(Main) {
                myJokesLiveData.value =
                    db.myJokesDao().getAllLikedJokes()
            }
        }
    }
}