package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityAboutBinding
import com.jimbonlemu.clefer.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.update_profile),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )


    }
}