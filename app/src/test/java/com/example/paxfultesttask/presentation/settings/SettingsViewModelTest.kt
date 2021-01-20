package com.example.paxfultesttask.presentation.settings

import android.content.Context
import com.example.paxfultesttask.data.models.JokesPreferences
import junit.framework.TestCase
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock

class SettingsViewModelTest : TestCase(){

    private lateinit var viewModel: SettingsViewModel

    @BeforeEach
    fun setup(){
        val context = mock(Context::class.java)
        viewModel = SettingsViewModel(context)
    }

    @org.junit.jupiter.api.Test
    fun test_checkPrefs(){
        viewModel.changePref(JokesPreferences(1,"vlad","Kernychnyi",1))
        assert(viewModel.jokePrefs.value == null)
    }

}