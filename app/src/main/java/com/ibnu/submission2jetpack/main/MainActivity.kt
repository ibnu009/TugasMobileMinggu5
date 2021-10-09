package com.ibnu.submission2jetpack.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibnu.submission2jetpack.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation_main)

        NavigationUI.setupWithNavController(navView, navController)
    }
}