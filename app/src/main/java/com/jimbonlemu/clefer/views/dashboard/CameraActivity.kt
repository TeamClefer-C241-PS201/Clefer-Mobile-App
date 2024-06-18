package com.jimbonlemu.clefer.views.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCameraBinding
import com.jimbonlemu.clefer.utils.CleferToast
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : CoreActivity<ActivityCameraBinding>() {


    private var imageCapture: ImageCapture? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this@CameraActivity)
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            }, ContextCompat.getMainExecutor(this@CameraActivity))

            captureImage.setOnClickListener {
                takePhoto()
            }

            switchCamera.setOnClickListener {
                switchCamera()
            }
        }

    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCameraBinding =
        ActivityCameraBinding.inflate(layoutInflater)

    private fun ActivityCameraBinding.bindCameraUseCases() {
        val rotation = viewFinder.display.rotation

        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector = cameraSelector

        val preview = Preview.Builder()
            .setTargetRotation(rotation)
            .build()

        imageCapture = ImageCapture.Builder()
            .setTargetRotation(rotation)
            .build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this@CameraActivity,
                cameraSelector,
                preview,
                imageCapture
            )
            preview.setSurfaceProvider(viewFinder.surfaceProvider)
        } catch (ex: Exception) {
            Log.e(TAG, "Use case binding failed", ex)
        }
    }

    private fun ActivityCameraBinding.takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = createFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this@CameraActivity),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                    val message = "Photo capture succeeded"
                    CleferToast.successToast(message,this@CameraActivity)
                    startActivity(
                        Intent(
                            this@CameraActivity,
                            PreviewImageActivity::class.java
                        ).putExtra(PreviewImageActivity.IMAGE_URI_VALUE, savedUri.toString())
                    )
                }

                override fun onError(exception: ImageCaptureException) {
                    val message = "Photo capture failed: ${exception.message}"
                    Snackbar.make(viewFinder, message, Snackbar.LENGTH_LONG).show()
                }
            }
        )
    }

    private fun ActivityCameraBinding.switchCamera() {
        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
        bindCameraUseCases()
    }


    private fun createFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDirectory = externalMediaDirs.firstOrNull()
        return File.createTempFile("IMG_${timestamp}_", ".jpg", storageDirectory)
    }


    companion object {
        private const val TAG = "CameraActivity"
    }
}