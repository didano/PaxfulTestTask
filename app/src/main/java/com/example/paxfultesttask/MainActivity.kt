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
            if (it.itemId == R.id.navMyJokes) {
                fragment.findNavController().navigate(R.id.action_global_myJokesFragment)
                root.close()
            } else if(it.itemId == R.id.navSettings){
                fragment.findNavController().navigate(R.id.action_global_settingsFragment)
                root.close()
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}