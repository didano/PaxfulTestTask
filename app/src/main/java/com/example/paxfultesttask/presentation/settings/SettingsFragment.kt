package com.example.paxfultesttask.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
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
        switchOfflineMode.isChecked = it.offlineMode.equals(1)
        tempPref.lastName = it.lastName
        tempPref.firstName = it.firstName
        tempPref.offlineMode = it.offlineMode
        charFirstName.hint = tempPref.firstName
        charLastName.hint = tempPref.lastName
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.jokePrefs.observe(viewLifecycleOwner, observer)
        vm.initPref()
        initFieldsData()
        initListeners()
    }

    private fun initFieldsData() {
        charFirstName.hint = tempPref.firstName
        charFirstName.hint = tempPref.lastName
    }

    override fun onStop() {
        super.onStop()
        vm.changePref(tempPref)
    }

    private fun initListeners(){
        charFirstName.addTextChangedListener{
            tempPref.firstName = it.toString()
            vm.changePref(tempPref)
        }

        charLastName.doOnTextChanged { text, start, before, count ->
            tempPref.lastName = text.toString()
            vm.changePref(tempPref)
        }

        switchOfflineMode.setOnCheckedChangeListener { button, checked ->
            if (checked) {
                tempPref.offlineMode = 1
            } else {
                tempPref.offlineMode = 0
            }
            vm.changePref(tempPref)
        }
    }
}