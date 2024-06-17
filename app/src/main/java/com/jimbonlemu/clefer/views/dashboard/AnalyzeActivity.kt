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
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.BottomNavigationBarActivity
import com.jimbonlemu.clefer.views.dashboard.viewmodels.HistoryViewModel
import com.jimbonlemu.clefer.views.dashboard.viewmodels.PredictViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AnalyzeActivity : CoreActivity<ActivityAnalyzeBinding>() {

    private val historyViewModel: HistoryViewModel by inject()
    private lateinit var imagePassed: String
    private lateinit var resultPassed: String
    private lateinit var descPassed: String
    private lateinit var suggestionPassed: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPassedValue()
        binding.apply {
            setupToolbar()
            setupAnalyzedResult()
            setupButtonAction()
        }
        onBackPressedDispatcher.addCallback(this@AnalyzeActivity) {
            intentBackToBottomNavbar()
        }
    }

    private fun initPassedValue() {
        imagePassed = intent.getStringExtra(PASSED_IMAGE).toString()
        resultPassed = intent.getStringExtra(PASSED_RESULT).toString()
        descPassed = intent.getStringExtra(PASSED_DESC).toString()
        suggestionPassed = intent.getStringExtra(PASSED_SUGGESTION).toString()
    }


    private fun ActivityAnalyzeBinding.setupAnalyzedResult() {
        Glide.with(this@AnalyzeActivity)
            .load(imagePassed.toUri())
            .into(ivAnalyzed)
        tvDiseaseResult.text = resultPassed
        tvDiseaseDesc.text = descPassed
        tvSuggestion.text = suggestionPassed

    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityAnalyzeBinding =
        ActivityAnalyzeBinding.inflate(layoutInflater)

    private fun ActivityAnalyzeBinding.setupButtonAction() {
        btnBack.setOnClickListener {
            intentBackToBottomNavbar()
        }

        btnSave.setOnClickListener {
            Prefs.apply {
                historyViewModel.insertHistoryAnalyzed(
                    HistoryAnalyzed(
                        ownerId = getUserId.toString(),
                        image = imagePassed,
                        diseaseResult = resultPassed,
                        diseaseDesc = descPassed,
                        diseaseSuggestion = suggestionPassed,
                        date = SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(Calendar.getInstance().time),
                    )
                )
            }
        }
    }

    private fun ActivityAnalyzeBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                intentBackToBottomNavbar()
            }
        }
    }

    private fun intentBackToBottomNavbar() {
        startActivity(Intent(this@AnalyzeActivity, BottomNavigationBarActivity::class.java))
        finish()
    }

    companion object {
        const val PASSED_IMAGE = "passed_image"
        const val PASSED_RESULT = "passed_result"
        const val PASSED_DESC = "passed_desc"
        const val PASSED_SUGGESTION = "passed_suggestion"
    }
}