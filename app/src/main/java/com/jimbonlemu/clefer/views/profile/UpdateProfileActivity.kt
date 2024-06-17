package com.jimbonlemu.clefer.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityUpdateProfileBinding
import com.jimbonlemu.clefer.utils.Prefs

class UpdateProfileActivity : CoreActivity<ActivityUpdateProfileBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setContentView(root)
            setupToolbar()
            setUserData()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityUpdateProfileBinding =
        ActivityUpdateProfileBinding.inflate(layoutInflater)

    private fun ActivityUpdateProfileBinding.setUserData() {
        Prefs.apply {
            Glide.with(this@UpdateProfileActivity).load(getPhoto?.toUri()).into(profileImage)
            edtEmail.inputText = getEmail.toString()
            edtUsername.inputText = getUsername.toString()
        }
    }

    private fun ActivityUpdateProfileBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}