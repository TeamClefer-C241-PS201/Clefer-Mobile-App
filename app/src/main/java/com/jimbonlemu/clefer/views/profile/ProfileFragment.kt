package com.jimbonlemu.clefer.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentProfileBinding


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
        }
    }

}