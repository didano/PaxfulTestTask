package com.example.paxfultesttask.presentation.myjokes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paxfultesttask.data.models.MyJoke
import com.example.paxfultesttask.R
import kotlinx.android.synthetic.main.joke_item.view.jokeText
import kotlinx.android.synthetic.main.myjoke_item.view.*

class MyJokesRecyclerAdapter : RecyclerView.Adapter<MyJokesRecyclerAdapter.MyJokeViewHolder>() {

    private var myJokesList: List<MyJoke> = emptyList()
    var onButtonClickListener: OnDeleteButtonClickListener? = null

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
        fun onDeleteButtonClicked(myJoke: MyJoke)
    }

    override fun onBindViewHolder(holder: MyJokeViewHolder, position: Int) {
        holder.jokeText.text = myJokesList[position].joke

        holder.deleteButton.setOnClickListener {
            onButtonClickListener?.onDeleteButtonClicked(myJokesList[position])
        }
    }

    fun newList(list:List<MyJoke>){
        myJokesList = list
        notifyDataSetChanged()
    }

}