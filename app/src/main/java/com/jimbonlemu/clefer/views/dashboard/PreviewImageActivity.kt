package com.jimbonlemu.clefer.views.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding

class PreviewImageActivity : CoreActivity<ActivityPreviewImageBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val passedValue = intent.getStringExtra(IMAGE_URI_VALUE)

        binding.apply {
            setContentView(root)
            setupToolbar()
            Glide.with(this@PreviewImageActivity)
                .load(Uri.parse(passedValue))
                .into(ivPreview)
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

    companion object {
        const val IMAGE_URI_VALUE = "image_uri_value"
    }
}