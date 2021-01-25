package com.example.paxfultesttask.presentation.myjokes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.presentation.jokeslist.adapter.JokesListAdapter
import com.example.paxfultesttask.utils.JokesDiffUtil
import kotlinx.android.synthetic.main.joke_item.view.jokeText
import kotlinx.android.synthetic.main.myjoke_item.view.*

class MyJokesRecyclerAdapter(private val onClick: OnDeleteButtonClickListener) :
    RecyclerView.Adapter<MyJokesRecyclerAdapter.MyJokeViewHolder>() {

    private var myJokesList: List<Joke> = emptyList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyJokesRecyclerAdapter.MyJokeViewHolder =
        MyJokeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.myjoke_item, parent, false)
        )


    override fun getItemCount(): Int = myJokesList.size

    inner class MyJokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jokeText: TextView = view.jokeText
        val deleteButton: Button = view.deleteButton
    }

    interface OnDeleteButtonClickListener {
        fun onDeleteButtonClicked(likedJoke: Joke)
    }

    override fun onBindViewHolder(holder: MyJokeViewHolder, position: Int) {
        holder.jokeText.text = myJokesList[position].joke

        holder.deleteButton.setOnClickListener {
            onClick.onDeleteButtonClicked(myJokesList[position])
        }
    }

    fun newList(list: List<Joke>) {
        val diffs = JokesDiffUtil(myJokesList,list)
        val result = DiffUtil.calculateDiff(diffs)
        myJokesList = list
        result.dispatchUpdatesTo(this)
    }
}