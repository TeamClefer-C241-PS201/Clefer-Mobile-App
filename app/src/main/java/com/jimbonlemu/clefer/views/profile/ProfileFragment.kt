package com.jimbonlemu.clefer.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentProfileBinding
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.auth.SignInActivity
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import org.koin.android.ext.android.inject


class ProfileFragment : CoreFragment<FragmentProfileBinding>() {
    private val authViewModel: AuthViewModels by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainButton()
            setUserData()
        }
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    private fun FragmentProfileBinding.setUserData() {
        username.text = Prefs.getUsername
        email.text = Prefs.getEmail
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

}