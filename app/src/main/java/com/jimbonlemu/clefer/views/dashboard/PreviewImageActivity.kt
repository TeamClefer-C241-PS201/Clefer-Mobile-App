package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding

class PreviewImageActivity : CoreActivity<ActivityPreviewImageBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            setupToolbar()
        }

    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityPreviewImageBinding =
        ActivityPreviewImageBinding.inflate(layoutInflater)

    private fun ActivityPreviewImageBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}