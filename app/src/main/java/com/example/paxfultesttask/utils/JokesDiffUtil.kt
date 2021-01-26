package com.example.paxfultesttask.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.paxfultesttask.data.models.Joke

class JokesDiffUtil(val oldList: List<Joke>, val newList: List<Joke>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJoke = oldList[oldItemPosition]
        val newJoke = newList[newItemPosition]
        return oldJoke.id == newJoke.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldJoke = oldList[oldItemPosition]
        val newJoke = newList[newItemPosition]
        return oldJoke === newJoke
    }
}