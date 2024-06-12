package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAnalyzeBinding

class AnalyzeActivity : CoreActivity<ActivityAnalyzeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAnalyzeBinding= ActivityAnalyzeBinding.inflate(layoutInflater)
}