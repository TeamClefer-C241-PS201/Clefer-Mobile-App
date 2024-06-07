package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.databinding.ActivitySaveArticleBinding

class SaveArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaveArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //add toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.save_article),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }


}