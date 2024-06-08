package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityAboutBinding
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //add toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.about_app),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
    }

    }
