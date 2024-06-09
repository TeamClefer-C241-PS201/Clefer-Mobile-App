package com.jimbonlemu.clefer.views.dashboard

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityPreviewImageBinding
import com.yalantis.ucrop.UCrop
import java.io.File
import java.util.Date

class PreviewImageActivity : CoreActivity<ActivityPreviewImageBinding>() {

    private lateinit var currentUriValue: Uri

    private val uCropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentUriValue = resultUri
                    updateImage(currentUriValue)
                }
            }else{
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
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityPreviewImageBinding =
        ActivityPreviewImageBinding.inflate(layoutInflater)

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