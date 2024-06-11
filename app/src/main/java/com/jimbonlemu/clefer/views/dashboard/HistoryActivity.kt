package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityHistoryBinding

class HistoryActivity : CoreActivity<ActivityHistoryBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityHistoryBinding = ActivityHistoryBinding.inflate(layoutInflater)
}