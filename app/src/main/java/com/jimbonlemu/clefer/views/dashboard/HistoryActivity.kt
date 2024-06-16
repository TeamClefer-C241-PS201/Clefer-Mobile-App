package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityHistoryBinding
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding

class HistoryActivity : CoreActivity<ActivityHistoryBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupToolbar()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityHistoryBinding = ActivityHistoryBinding.inflate(layoutInflater)

    private fun ActivityHistoryBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}