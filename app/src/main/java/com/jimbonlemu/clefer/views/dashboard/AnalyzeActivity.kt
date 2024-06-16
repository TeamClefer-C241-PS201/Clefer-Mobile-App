package com.jimbonlemu.clefer.views.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.addCallback
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityAnalyzeBinding
import com.jimbonlemu.clefer.views.BottomNavigationBarActivity

class AnalyzeActivity : CoreActivity<ActivityAnalyzeBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupToolbar()
            setupAnalyzedResult()
            onBackPressedDispatcher.addCallback(this@AnalyzeActivity) {
                startActivity(Intent(this@AnalyzeActivity, BottomNavigationBarActivity::class.java))
                finish()
            }
        }
    }


    private fun ActivityAnalyzeBinding.setupAnalyzedResult() {
        Glide.with(this@AnalyzeActivity)
            .load(intent.getStringExtra(PASSED_IMAGE)?.toUri())
            .into(ivAnalyzed)
        tvDiseaseResult.text = intent.getStringExtra(PASSED_RESULT)
        tvDiseaseDesc.text = intent.getStringExtra(PASSED_DESC)
        tvSuggestion.text = intent.getStringExtra(PASSED_SUGGESTION)
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAnalyzeBinding =
        ActivityAnalyzeBinding.inflate(layoutInflater)

    private fun ActivityAnalyzeBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                tvDiseaseResult.text = ""
                tvDiseaseDesc.text = ""
                tvSuggestion.text = ""
                ivAnalyzed.setImageURI(null)
                startActivity(Intent(this@AnalyzeActivity, BottomNavigationBarActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        const val PASSED_IMAGE = "passed_image"
        const val PASSED_RESULT = "passed_result"
        const val PASSED_DESC = "passed_desc"
        const val PASSED_SUGGESTION = "passed_suggestion"
    }
}