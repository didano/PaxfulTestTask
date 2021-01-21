package com.example.paxfultesttask.presentation.settings

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.JokesPreferences
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {

    private val vm: SettingsViewModel by viewModel()
    private var tempPreference: JokesPreferences = JokesPreferences()
    private val observer = Observer<JokesPreferences> {
        Log.d("PREFTEMPINIT", it.toString())
        tempPreference = it
        fillFields(it)
        switchOfflineMode.isChecked = it.offlineMode.equals(1)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.jokePrefs.observe(viewLifecycleOwner, observer)
        initListeners()
    }

    private fun fillFields(preferences: JokesPreferences) {
        if (preferences.firstName.isEmpty() && preferences.lastName.isEmpty()) {
            charFirstName.text = SpannableStringBuilder("Chuck")
            charLastName.text = SpannableStringBuilder("Norris")
        } else if (preferences.firstName.isEmpty()) {
            charFirstName.text = SpannableStringBuilder(preferences.firstName)
            charLastName.text = SpannableStringBuilder("Norris")
        } else if (preferences.lastName.isEmpty()) {
            charFirstName.text = SpannableStringBuilder("Chuck")
            charLastName.text = SpannableStringBuilder(preferences.lastName)
        } else {
            charFirstName.text = SpannableStringBuilder(preferences.firstName)
            charLastName.text = SpannableStringBuilder(preferences.lastName)
        }
    }

    private fun initListeners() {
        charFirstName.doOnTextChanged { text, _, _, _ ->
            tempPreference.firstName = text.toString().trim()
            vm.changePref(tempPreference)
        }

        charLastName.doOnTextChanged { text, _, _, _ ->
            tempPreference.lastName = text.toString().trim()
            vm.changePref(tempPreference)
        }
        switchOfflineMode.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                tempPreference.offlineMode = 1
                vm.changePref(tempPreference)
            } else {
                tempPreference.offlineMode = 0
                vm.changePref(tempPreference)
            }
        }
    }
}