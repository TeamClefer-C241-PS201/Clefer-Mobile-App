package com.jimbonlemu.clefer.views

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityBottomNavigationBarBinding

class BottomNavigationBarActivity : CoreActivity<ActivityBottomNavigationBarBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupBottomNavigationBar()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityBottomNavigationBarBinding =
        ActivityBottomNavigationBarBinding.inflate(layoutInflater)

    private fun ActivityBottomNavigationBarBinding.setupBottomNavigationBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.hide()
        val navController =
            findNavController(R.id.navigation_host_fragment_activity_bottom_navigation_bar)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_article, R.id.navigation_dashboard, R.id.navigation_profile)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}