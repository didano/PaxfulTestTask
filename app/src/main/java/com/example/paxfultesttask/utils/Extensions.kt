package com.example.paxfultesttask.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asImmutable(): LiveData<T> = this

fun Context.sendShareIntent(text:String){
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(intent, "Share with friend: ")
    startActivity(shareIntent)
}

fun Context.showToast(text:String) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()