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
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.presentation.jokeslist.adapter.JokesListAdapter
import kotlinx.android.synthetic.main.jokes_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class JokesListFragment : Fragment() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorListener: SensorEventListener
    private var accelValue:Float = SensorManager.GRAVITY_EARTH
    private var accelLast:Float = SensorManager.GRAVITY_EARTH
    private var shake:Float = 0.0f
    private val jokesAdapter: JokesListAdapter by lazy {
        JokesListAdapter()
    }

    val vm: JokesListViewModel by viewModel()

    private val observer = Observer<List<Joke>> {
        jokesAdapter.newList(it)
    }

    var needToRefresh:Boolean = true

    private val prefsObserver = Observer<JokesPreferences>{
        if(it.offlineMode == 0){
            needToRefresh = true
            validateName(it.firstName,it.lastName)
        } else {
            needToRefresh = false
            vm.fillLiveDataOffline()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.jokes_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.jokeTextLiveData.observe(viewLifecycleOwner, observer)
        vm.jokesPreferences.observe(viewLifecycleOwner,prefsObserver)
        registerShakeListener()
        initClickListener()
        jokesRecyclerView.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initClickListener(){
        jokesAdapter.onButtonClickListener = object : JokesListAdapter.OnButtonClickListener{
            override fun onShareButtonClick(jokeText: String) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, jokeText)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent,"Share with friend: ")
                startActivity(shareIntent)
            }

            override fun onLikeButtonClick(joke: Joke) {
                vm.likeJoke(joke)
                Toast.makeText(requireContext(),"Added to MyJokes",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validateName(firstName:String, lastName:String){
        if(firstName.isNotEmpty() && lastName.isNotEmpty()){
            vm.fillLiveDataWithNamedJokes(firstName,lastName)
        } else if(firstName.isNotEmpty()){
            vm.fillLiveDataWithNamedJokes(name = firstName )
        } else if(lastName.isNotEmpty()){
            vm.fillLiveDataWithNamedJokes(lastname = lastName)
        } else {
            vm.fillLiveData()
        }
    }

    private fun registerShakeListener(){
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
                accelValue = Math.sqrt((x!!.times(x)+y!!.times(y)+z!!.times(z)).toDouble()).toFloat()
                val delta = accelValue - accelLast
                shake = shake * 0.9f + delta
                if(needToRefresh&&shake>8){
                    refresh()
                } else if (!needToRefresh&&shake>8) {
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

    fun refresh(){
        val firstName = vm.jokesPreferences.value!!.firstName
        val lastName = vm.jokesPreferences.value!!.lastName
        validateName(firstName,lastName)
    }

    override fun onResume() {
        super.onResume()
        vm.checkOfflineMode()
    }
}