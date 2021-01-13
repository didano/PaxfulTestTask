package com.example.paxfultesttask.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.paxfultesttask.R
import com.example.paxfultesttask.core.domain.JokesPreferences
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.dsl.module

val settingsFragmentModule = module {
    factory { SettingsFragment() }
}

class SettingsFragment : Fragment() {

    private val vm: SettingsViewModel by viewModel()
    var tempPref = JokesPreferences()

    private val observer = Observer<JokesPreferences> {
        charLastName.setText(it.lastName)
        charFirstName.setText(it.firstName)
        switchOfflineMode.isChecked = it.offlineMode.equals(1)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.jokePrefs.observe(viewLifecycleOwner, observer)
        vm.initPref()
        initListeners()
    }

    override fun onPause() {
        super.onPause()
        vm.changePref(tempPref)
    }

    private fun initListeners(){
        charFirstName.addTextChangedListener {
            tempPref.firstName = it.toString()
        }

        charLastName.addTextChangedListener {
            tempPref.lastName = it.toString()
        }

        switchOfflineMode.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                tempPref.offlineMode = 1
            } else {
                tempPref.offlineMode = 0
            }
        }
    }
}