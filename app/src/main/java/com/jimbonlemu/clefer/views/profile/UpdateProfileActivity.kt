package com.jimbonlemu.clefer.views.profile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityUpdateProfileBinding
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.profile.viewmodels.ProfileViewModel
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class UpdateProfileActivity : CoreActivity<ActivityUpdateProfileBinding>() {
    private val profileViewModel: ProfileViewModel by inject()
    private var currentUriValue: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            setupToolbar()
            Prefs.apply {
                setUserData(getPhoto, getName, getEmail, getUsername)
            }

            btnSaveProfile.setOnClickListener {
                profileViewModel.updateUser(
                    name = edtUserName.inputText,
                    userPhotoUri = currentUriValue,
                    username = edtUsername.inputText,
                    email = edtEmail.inputText
                )
            }

            editProfileText.setOnClickListener {
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
        initObserverUpdateUser()
        initObserverGetUserData()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityUpdateProfileBinding =
        ActivityUpdateProfileBinding.inflate(layoutInflater)

    private fun ActivityUpdateProfileBinding.setUserData(
        image: String,
        name: String,
        email: String,
        username: String,
    ) {

        Glide.with(this@UpdateProfileActivity).load(image.toUri()).into(profileImage)
        edtUserName.inputText = name
        edtEmail.inputText = email
        edtUsername.inputText = username
    }

    private fun ActivityUpdateProfileBinding.setupToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun initObserverUpdateUser() {
        profileViewModel.apply {
            updateUserState.observe(this@UpdateProfileActivity) { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        Toast.makeText(this@UpdateProfileActivity, "Memproses perubahan", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is ResponseState.Success -> {
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Profil Berhasil Diperbarui",
                            Toast.LENGTH_SHORT
                        ).show()
                        getUserData()
                    }

                    is ResponseState.Error -> {
                        Toast.makeText(
                            this@UpdateProfileActivity,
                            "Terjadi kesalahan: Profil gagal diperbarui",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun initObserverGetUserData() {
        profileViewModel.getUserState.observe(this@UpdateProfileActivity) { state ->
            when (state) {
                is ResponseState.Loading -> {
                }

                is ResponseState.Success -> {
                    state.data.apply {
                        binding.setUserData(
                            userPhoto.toString(),
                            name.toString(),
                            email.toString(),
                            username.toString()
                        )
                    }
                }

                is ResponseState.Error -> {

                }
            }
        }
    }

    private fun launchUCrop(uri: Uri) {
        UCrop.of(uri, Uri.fromFile(File(this.cacheDir, "cropped_profile_image_${Date().time}.jpg")))
            .withAspectRatio(1f, 1f).getIntent(this).apply {
                uCropLauncher.launch(this)
            }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            launchUCrop(uri)
        }
    }

    private val uCropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentUriValue = resultUri
                    Glide.with(this).load(resultUri).into(binding.profileImage)
                }
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
}