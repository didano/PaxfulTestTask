package com.example.paxfultesttask.presentation.myjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.presentation.AddJokeDialog
import com.example.paxfultesttask.presentation.base.BaseFragment
import com.example.paxfultesttask.presentation.myjokes.adapter.MyJokesRecyclerAdapter
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyJokesFragment : BaseFragment() {

    private val myJokesAdapter by lazy {
        MyJokesRecyclerAdapter(object : MyJokesRecyclerAdapter.OnDeleteButtonClickListener {
            override fun onDeleteButtonClicked(likedJoke: Joke) {
                vm.deleteMyJoke(likedJoke)
            }
        })
    }

    private val dialog by lazy {
        AddJokeDialog(requireContext(), object : AddJokeDialog.OnDialogButtonClickListener {
            override fun onCancelButtonClick(dialog: AddJokeDialog) {
                dialog.dismiss()
            }

            override fun onSaveButtonClick(dialog: AddJokeDialog) {
                vm.saveMyJoke(Joke(joke = dialog.dialogEditText.text.toString(), isLiked = true))
                dialog.dismiss()
            }

        })
    }

    val vm: MyJokesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myJokesRecyclerView.apply {
            adapter = myJokesAdapter
            layoutManager = LinearLayoutManager(context)
        }
        createNewJoke.setOnClickListener {
            dialog.show()
        }
    }

    override fun observeViewModel() {
        vm.myJokesLiveData.observe(this){
            myJokesAdapter.newList(it)
        }
    }

}