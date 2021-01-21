package com.example.paxfultesttask.presentation.jokeslist

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.presentation.jokeslist.adapter.JokesListAdapter
import kotlinx.android.synthetic.main.jokes_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesListFragment : Fragment() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorListener: SensorEventListener
    private var accelValue: Float = SensorManager.GRAVITY_EARTH
    private var accelLast: Float = SensorManager.GRAVITY_EARTH
    private var shake: Float = 0.0f
    private val jokesAdapter: JokesListAdapter by lazy {
        JokesListAdapter()
    }

    val vm: JokesListViewModel by viewModel()

    private val observer = Observer<List<Joke>> {

        jokesAdapter.newList(it)
    }

    private val refreshObserver = Observer<Boolean> {
        if (it == true) {
            jokesRecyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            jokesRecyclerView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.jokes_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.jokeTextLiveData.observe(viewLifecycleOwner, observer)
        vm.isRefreshed.observe(viewLifecycleOwner, refreshObserver)
        registerShakeListener()
        initClickListener()
        jokesRecyclerView.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initClickListener() {
        jokesAdapter.onButtonClickListener = object : JokesListAdapter.OnButtonClickListener {
            override fun onShareButtonClick(jokeText: String) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, jokeText)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "Share with friend: ")
                startActivity(shareIntent)
            }

            override fun onLikeButtonClick(joke: Joke) {
                vm.likeJoke(joke)
                Toast.makeText(requireContext(), "Added to MyJokes", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun registerShakeListener() {
        sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                val x = event?.let {
                    it.values[0]
                }
                val y = event?.let {
                    it.values[1]
                }
                val z = event?.let {
                    it.values[2]
                }
                accelLast = accelValue
                accelValue =
                    Math.sqrt((x!!.times(x) + y!!.times(y) + z!!.times(z)).toDouble()).toFloat()
                val delta = accelValue - accelLast
                shake = shake * 0.9f + delta
                if (vm.needToRefresh && shake > 8) {
                    vm.checkOfflineMode()
                } else if (!vm.needToRefresh && shake > 8) {
                    vm.getRandomJoke()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
        sensorManager =
            (requireActivity().getSystemService(Context.SENSOR_SERVICE)) as SensorManager
        sensorManager.registerListener(
            sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onResume() {
        activity?.title = "Jokes List"
        super.onResume()
        vm.checkOfflineMode()
    }
}