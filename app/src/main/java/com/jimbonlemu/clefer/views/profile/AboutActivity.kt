package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAboutBinding

class AboutActivity : CoreActivity<ActivityAboutBinding>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupToolbar()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAboutBinding =
        ActivityAboutBinding.inflate(layoutInflater)

    private fun ActivityAboutBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}
