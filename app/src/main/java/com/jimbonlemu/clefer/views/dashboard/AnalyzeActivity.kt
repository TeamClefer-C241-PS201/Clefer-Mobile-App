package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAnalyzeBinding

class AnalyzeActivity : CoreActivity<ActivityAnalyzeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAnalyzeBinding= ActivityAnalyzeBinding.inflate(layoutInflater)
}