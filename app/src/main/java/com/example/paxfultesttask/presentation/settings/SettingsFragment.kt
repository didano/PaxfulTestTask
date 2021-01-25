package com.example.paxfultesttask.presentation.settings

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.observe
import com.example.paxfultesttask.R
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : BaseFragment() {

    private val vm: SettingsViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    override fun observeViewModel() {
        vm.jokePrefsFirstName.observe(this){
            Log.d("SETTINGSFirst",it.toString())
            charFirstName.setText(it)
        }

        vm.jokePrefsLastName.observe(this){
            Log.d("SETTINGSLast",it.toString())
            charLastName.setText(it)
        }

        vm.isOffline.observe(this){
            switchOfflineMode.isChecked = it
        }
    }

//    private fun fillFields(preferences: JokesPreferences) {
//        charFirstName.text = SpannableStringBuilder(preferences.firstName)
//        charLastName.text = SpannableStringBuilder(preferences.lastName)
//        switchOfflineMode.isChecked = preferences.offlineMode
//    }

    private fun initListeners() {
        charFirstName.doOnTextChanged { text, _, _, _ ->
            vm.updatePrefFirstName(text.toString().trim())
        }

        charLastName.doOnTextChanged { text, _, _, _ ->
            vm.updatePrefLastName(text.toString().trim())
        }
        switchOfflineMode.setOnCheckedChangeListener { _, checked ->
            vm.updateOfflineMode(checked)
        }
    }
}