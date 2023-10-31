package com.hgtech.smartio.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hgtech.smartio.R
import com.hgtech.smartio.databinding.ActivityMainBinding
import com.hgtech.smartio.network.manager.UserWrapper.USER_ID
import com.hgtech.smartio.network.manager.UserWrapper.setID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        setID(intent.getStringExtra(USER_ID))
    }
}