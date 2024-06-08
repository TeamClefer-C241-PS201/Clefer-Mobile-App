package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding
import com.jimbonlemu.clefer.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : CoreActivity<ActivityUpdateProfileBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            // Set up Toolbar
            setupToolbar()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityUpdateProfileBinding =
        ActivityUpdateProfileBinding.inflate(layoutInflater)

    private fun ActivityUpdateProfileBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}