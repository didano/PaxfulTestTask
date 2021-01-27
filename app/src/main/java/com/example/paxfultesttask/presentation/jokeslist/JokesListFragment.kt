package com.example.paxfultesttask.presentation.jokeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.presentation.base.BaseFragment
import com.example.paxfultesttask.presentation.jokeslist.adapter.JokesListAdapter
import com.example.paxfultesttask.utils.ShakeDetectionUtil
import com.example.paxfultesttask.utils.sendShareIntent
import com.example.paxfultesttask.utils.showToast
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.jokes_list_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesListFragment : BaseFragment(), ShakeDetector.Listener {

    private val jokesAdapter: JokesListAdapter by lazy {
        JokesListAdapter(object : JokesListAdapter.OnButtonClickListener {
            override fun onShareButtonClick(jokeText: String) {
                requireContext().sendShareIntent(jokeText)
            }

            override fun onLikeButtonClick(joke: Joke) {
                vm.likeJoke(joke)
            }
        })
    }

    private val shakeDetector: ShakeDetectionUtil by inject()

    val vm: JokesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.jokes_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jokesRecyclerView.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(context)
        }
        shakeDetector.init(this)

        swipeLayout.setOnRefreshListener {
            vm.initData()
        }

        shakeDetector.start()
    }

    override fun observeViewModel() {
        vm.apply {

            jokeTextMutable.observe(this@JokesListFragment) {
                jokesAdapter.newList(it)
            }

            jokeLiked.observe(this@JokesListFragment) {
                requireContext().showToast(getString(R.string.jokes_added_toast))
            }

            isRefreshed.observe(this@JokesListFragment) {
                if (it) {
                    progressBar.visibility = View.GONE
                    swipeLayout.isRefreshing = false
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }

            changeFragmentTitle.observe(this@JokesListFragment) {
                requireActivity().title = it
            }

            showErrorToast.observe(this@JokesListFragment){
                requireContext().showToast(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shakeDetector.stop()
    }

    override fun onResume() {
        super.onResume()
        vm.changeTitle("Jokes List")
    }

    override fun hearShake() {
        vm.onShakeAction()
    }
}