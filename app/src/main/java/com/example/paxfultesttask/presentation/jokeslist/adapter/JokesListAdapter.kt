package com.example.paxfultesttask.presentation.jokeslist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.R
import kotlinx.android.synthetic.main.joke_item.view.*

class JokesListAdapter() :
    RecyclerView.Adapter<JokesListAdapter.JokeViewHolder>() {

    private var jokesList: List<Joke> = emptyList()
    var onButtonClickListener:OnButtonClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder =
        JokeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)
        )


    override fun getItemCount(): Int = jokesList.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeText.text = jokesList[position].joke

        holder.likeButton.setOnClickListener {
            onButtonClickListener?.onLikeButtonClick(jokesList[position])
        }

        holder.shareButton.setOnClickListener {
            onButtonClickListener?.onShareButtonClick(holder.jokeText.text.toString())
        }
    }

    inner class JokeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jokeText: TextView = view.jokeText
        val likeButton:Button = view.likeButton
        val shareButton:Button = view.shareButton
    }

    interface OnButtonClickListener {
        fun onShareButtonClick(jokeText:String)
        fun onLikeButtonClick(joke: Joke)
    }

    fun newList(list: List<Joke>){
        jokesList = list
        this.notifyDataSetChanged()
    }
}