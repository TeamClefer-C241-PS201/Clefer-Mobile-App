package com.jimbonlemu.clefer.views.profile

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityUpdateProfileBinding
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.profile.viewmodels.ProfileViewModel
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class UpdateProfileActivity : CoreActivity<ActivityUpdateProfileBinding>() {
    private val profileViewModel: ProfileViewModel by inject()
    private var currentUriValue: Uri? = Prefs.getPhoto.toUri()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            setupToolbar()
            Prefs.apply {
                setUserData(getPhoto, getName, getEmail, getUsername)
                btnSaveProfile.setOnClickListener {
                    val userName = edtUserName.inputText
                    val photo = currentUriValue
                    val username = edtUsername.inputText
                    val email = edtEmail.inputText
                    if (userName != getName || photo != getPhoto.toUri() || username != getUsername || email != getEmail) {
                        profileViewModel.updateUser(
                            name = edtUserName.inputText,
                            userPhotoUri = currentUriValue,
                            username = edtUsername.inputText,
                            email = edtEmail.inputText
                        )
                    }else{
                        CleferToast.informToast("Tidak ada perubahan yang dilakukan", this@UpdateProfileActivity)
                    }
                }
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
                        CleferToast.informToast(
                            "Memproses perubahan....",
                            this@UpdateProfileActivity
                        )
                        setEnableComponent(false)
                    }

                    is ResponseState.Success -> {
                        CleferToast.successToast(
                            "Profil Berhasil Diperbarui",
                            this@UpdateProfileActivity
                        )
                        getUserData()
                        setEnableComponent(true)
                    }

                    is ResponseState.Error -> {
                        CleferToast.errorToast(
                            "Terjadi kesalahan, Profil gagal diperbarui",
                            this@UpdateProfileActivity
                        )
                        setEnableComponent(true)
                    }
                }
            }
        }
    }

    private fun initObserverGetUserData() {
        profileViewModel.getUserState.observe(this@UpdateProfileActivity) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    setShimmerStatus(true)
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
                    setShimmerStatus(false)
                }

                is ResponseState.Error -> {
                    setShimmerStatus(false)
                }
            }
        }
    }

    private fun setEnableComponent(isEnable: Boolean) {
        binding.apply {
            btnSaveProfile.isEnabled = isEnable
            edtUserName.isEnabled = isEnable
            edtUsername.isEnabled = isEnable
            edtEmail.isEnabled = isEnable
            editProfileText.isEnabled = isEnable
        }
    }

    private fun setShimmerStatus(isEnable: Boolean) {
        binding.apply {
            if (isEnable) {
                shimmerProfile.startShimmer()
                shimmerProfile.visibility = View.VISIBLE
                profileImage.visibility = View.INVISIBLE
            } else {
                shimmerProfile.stopShimmer()
                shimmerProfile.visibility = View.GONE
                profileImage.visibility = View.VISIBLE
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