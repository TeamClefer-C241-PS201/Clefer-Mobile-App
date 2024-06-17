package com.jimbonlemu.clefer.views.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentDashboardBinding
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.community.CommunityActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class DashboardFragment : CoreFragment<FragmentDashboardBinding>() {

    private var cameraExecutor: ExecutorService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentDashboardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonAction()
        binding.apply {
            tvUserName.text = Prefs.getName
            Glide.with(requireActivity()).load(Prefs.getPhoto?.toUri()).into(ivUserProfile)
        }

    }

    private fun setupButtonAction() {
        binding.apply {
            cardCamera.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startActivity(Intent(requireActivity(), CameraActivity::class.java))
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            }

            cardGallery.setOnClickListener {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            cardCommunity.setOnClickListener {
                startActivity(Intent(requireActivity(), CommunityActivity::class.java))
            }

            cardHistory.setOnClickListener {
                startActivity(Intent(requireActivity(), HistoryActivity::class.java))
            }

        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startActivity(
                Intent(requireActivity(), PreviewImageActivity::class.java).putExtra(
                    PreviewImageActivity.IMAGE_URI_VALUE,
                    uri.toString()
                )
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor?.shutdown()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 123
    }

}