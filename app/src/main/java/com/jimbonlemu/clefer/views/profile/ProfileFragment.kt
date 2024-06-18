package com.jimbonlemu.clefer.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentProfileBinding
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.auth.SignInActivity
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import com.jimbonlemu.clefer.views.profile.viewmodels.ProfileViewModel
import org.koin.android.ext.android.inject

class ProfileFragment : CoreFragment<FragmentProfileBinding>() {
    private val authViewModel: AuthViewModels by inject()
    private val profileViewModel: ProfileViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainButton()
            profileViewModel.getUserData()
            initObserverGetUserData()
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getUserData()
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    private fun setUserData(nameValue: String, emailValue: String, photoValue: String) {
        binding.apply {
            username.text = nameValue
            email.text = emailValue
            Glide.with(requireActivity()).load(photoValue.toUri()).into(profileImage)

        }
    }

    private fun FragmentProfileBinding.mainButton() {
        btnInfoApp.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
        btnUpdateProfile.setOnClickListener {
            startActivity(Intent(requireContext(), UpdateProfileActivity::class.java))
        }
        btnLogout.setOnClickListener {
            authViewModel.logout()
            startActivity(Intent(requireActivity(), SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
            requireActivity().finish()
        }

    }

    private fun initObserverGetUserData() {
        profileViewModel.getUserState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResponseState.Loading -> {
                    setupShimmerStatus(true)
                }

                is ResponseState.Success -> {
                    setupShimmerStatus(false)
                    state.data.apply {
                        setUserData(
                            name.toString(),
                            email.toString(),
                            userPhoto.toString(),
                        )
                    }
                }

                is ResponseState.Error -> {
                    setupShimmerStatus(false)
                    binding.apply {
                        Prefs.apply {
                            setUserData(getName, getEmail, getPhoto)
                        }
                    }
                }
            }
        }
    }

    private fun setupShimmerStatus(isEnable: Boolean) {
        binding.apply {
            if (isEnable) {
                shimmerProfile.startShimmer()
                shimmerProfile.visibility = View.VISIBLE
            }
            else{
                shimmerProfile.stopShimmer()
                shimmerProfile.visibility = View.INVISIBLE
            }
        }
    }

}