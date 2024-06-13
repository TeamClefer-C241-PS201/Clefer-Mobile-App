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


class ProfileFragment : CoreFragment<FragmentProfileBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainButton()
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)


    private fun mainButton() {
        binding.apply {
            btnInfoApp.setOnClickListener {
                startActivity(Intent(requireContext(), AboutActivity::class.java))
            }
            btnUpdateProfile.setOnClickListener {
                startActivity(Intent(requireContext(), UpdateProfileActivity::class.java))
            }
            btnLogout.setOnClickListener {
                Prefs.clearAllPreferences()
                startActivity(Intent(requireActivity(), SignInActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
                requireActivity().finish()
            }
        }
    }

}