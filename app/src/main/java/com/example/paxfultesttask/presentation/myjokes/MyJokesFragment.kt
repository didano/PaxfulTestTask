package com.example.paxfultesttask.presentation.myjokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.MyJoke
import com.example.paxfultesttask.presentation.AddJokeDialog
import com.example.paxfultesttask.presentation.myjokes.adapter.MyJokesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_my_jokes.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyJokesFragment : Fragment() {

    private val myJokesAdapter by lazy {
        MyJokesRecyclerAdapter()
    }

    val vm: MyJokesViewModel by viewModel()
    private val observer = Observer<List<MyJoke>> {
        myJokesAdapter.newList(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.myJokesLiveData.observe(viewLifecycleOwner, observer)
        myJokesRecyclerView.apply {
            adapter = myJokesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        initClickListener()
    }

    private fun initClickListener() {
        myJokesAdapter.onButtonClickListener =
            object : MyJokesRecyclerAdapter.OnDeleteButtonClickListener {
                override fun onDeleteButtonClicked(myJoke: MyJoke) {
                    vm.deleteMyJoke(myJoke)
                }
            }
        createNewJoke.setOnClickListener {
            showDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        vm.fillMyJokesLiveData()
    }

    private fun showDialog() {
        val dialog = AddJokeDialog(requireContext())
        dialog.clickListener = object : AddJokeDialog.OnDialogButtonClickListener {
            override fun onCancelButtonClick() {
                dialog.dismiss()
            }

            override fun onSaveButtonClick(myJoke: MyJoke) {
                vm.saveMyJoke(myJoke)
                dialog.dismiss()
            }

        }
        dialog.show()
    }
}