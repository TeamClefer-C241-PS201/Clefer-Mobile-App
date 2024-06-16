package com.jimbonlemu.clefer.views.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.dashboard.viewmodels.PredictViewModel
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class PreviewImageActivity : CoreActivity<ActivityPreviewImageBinding>() {

    private lateinit var currentUriValue: Uri
    private val predictViewModel: PredictViewModel by inject()

    private val uCropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentUriValue = resultUri
                    updateImage(currentUriValue)
                }
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUriValue = Uri.parse(intent.getStringExtra(IMAGE_URI_VALUE))
        binding.apply {
            setContentView(root)
            setupToolbar()
            launchUCrop(currentUriValue)

            btnStartAnalyze.setOnClickListener {
                predictViewModel.predictImage(currentUriValue)
            }
            initObserver()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityPreviewImageBinding =
        ActivityPreviewImageBinding.inflate(layoutInflater)

    private fun initObserver() {
        predictViewModel.predictState.observe(this@PreviewImageActivity) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    binding.btnStartAnalyze.apply {
                        text = "Loading..."
                        isEnabled = false
                    }
                }

                is ResponseState.Success -> {
                    binding.btnStartAnalyze.apply {
                        text = getString(R.string.title_start_analyze)
                        isEnabled = true
                    }
                    startActivity(
                        Intent(
                            this@PreviewImageActivity,
                            AnalyzeActivity::class.java
                        ).apply {
                            state.data.data?.apply {
                                putExtra(AnalyzeActivity.PASSED_IMAGE, currentUriValue.toString())
                                putExtra(AnalyzeActivity.PASSED_RESULT, result)
                                putExtra(AnalyzeActivity.PASSED_DESC, description)
                                putExtra(AnalyzeActivity.PASSED_SUGGESTION, suggestion)
                            }
                        })
                    Toast.makeText(
                        this@PreviewImageActivity,
                        "Prediction successful: ${state.data.message}",
                        Toast.LENGTH_SHORT
                    ).show()
//                    state.data.data?.apply {
//                        Log.d(
//                            "IMAGE SCANNED RESULT",
//                            "$result \n $description \n $suggestion"
//                        )
//                    }
                }

                is ResponseState.Error -> {
                    binding.btnStartAnalyze.apply {
                        text = getString(R.string.title_start_analyze)
                        isEnabled = true
                    }
                    Toast.makeText(
                        this@PreviewImageActivity,
                        "Error: ${state.errorMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun ActivityPreviewImageBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun launchUCrop(uri: Uri) {
        val cachedImage = File(this.cacheDir, "cropped_image_${Date().time}.jpg")
        val destinationUri = Uri.fromFile(cachedImage)
        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)
        uCrop.getIntent(this).apply {
            uCropLauncher.launch(this)
        }
    }

    private fun updateImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .into(binding.ivPreview)
    }

    companion object {
        const val IMAGE_URI_VALUE = "image_uri_value"
    }
}