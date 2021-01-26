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
    }

    override fun observeViewModel() {
        vm.isShakeEnabled.observe(this) {
            enableShake(it)
        }

        vm.jokeTextMutable.observe(this) {
            jokesAdapter.newList(it)
        }

        vm.jokeLiked.observe(this) {
            requireContext().showToast(getString(R.string.jokes_added_toast))
        }

        vm.isRefreshed.observe(this) {
            if (it) {
                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.VISIBLE
            }
        }

        vm.swipeRefreshed.observe(this) {
            if (it) {
                swipeLayout.isRefreshing = false
            }
        }
    }

    private fun enableShake(enable: Boolean) {
        if (enable) {
            shakeDetector.start()
        } else {
            shakeDetector.stop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        shakeDetector.stop()
    }

    override fun onResume() {
        super.onResume()
        activity?.title = "Jokes List"
    }

    override fun hearShake() {
        vm.onShakeAction()
    }
}