package com.example.paxfultesttask

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toggle = ActionBarDrawerToggle(
            this,
            root,
            R.string.navigation_drawer_open,
            R.string.material_drawer_close
        )
        root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navMyJokes -> {
                    fragment.findNavController().navigate(R.id.action_global_myJokesFragment)
                    title = getString(R.string.my_jokes_frag_title)
                    root.close()
                }
                R.id.navSettings -> {
                    fragment.findNavController().navigate(R.id.action_global_settingsFragment)
                    title = getString(R.string.settings_frag_title)
                    root.close()
                }
                R.id.navHome -> {
                    fragment.findNavController().navigate(R.id.action_global_jokesListFragment)
                    title = getString(R.string.jokes_list_frag_title)
                    root.close()
                }
            }
            return@setNavigationItemSelectedListener true
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.statusBarColor = getColor(R.color.dark_purple)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}