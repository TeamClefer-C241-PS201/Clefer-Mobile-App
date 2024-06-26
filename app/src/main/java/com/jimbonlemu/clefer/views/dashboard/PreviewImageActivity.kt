package com.jimbonlemu.clefer.views.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding
import com.jimbonlemu.clefer.utils.CleferToast
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
            setupToolbar(mToolbar)
            launchUCrop(currentUriValue)

            btnStartAnalyze.setOnClickListener {
                predictViewModel.predictImage(currentUriValue)
            }
            initObserver()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityPreviewImageBinding =
        ActivityPreviewImageBinding.inflate(layoutInflater)

    private fun ActivityPreviewImageBinding.initObserver() {
        predictViewModel.predictState.observe(this@PreviewImageActivity) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    isButtonAnalyzedEnabled(false)
                }

                is ResponseState.Success -> {
                    CleferToast.successToast("Gambar berhasil di deteksi!", this@PreviewImageActivity)
                    btnStartAnalyze.apply {
                        text = getString(R.string.title_start_analyze)
                        isEnabled = false
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
                                putExtra(AnalyzeActivity.IS_VIEWING_HISTORY, false)
                            }
                        })

                }

                is ResponseState.Error -> {
                    CleferToast.errorToast("Gagal terdeteksi: ${state.errorMessage}", this@PreviewImageActivity)
                    isButtonAnalyzedEnabled(true)
                }
            }
        }
    }

    private fun ActivityPreviewImageBinding.isButtonAnalyzedEnabled(isEnable: Boolean) {
        btnStartAnalyze.isEnabled = isEnable
        btnStartAnalyze.text =
            if (isEnable) getString(R.string.title_start_analyze) else getString(R.string.title_processing_image)
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