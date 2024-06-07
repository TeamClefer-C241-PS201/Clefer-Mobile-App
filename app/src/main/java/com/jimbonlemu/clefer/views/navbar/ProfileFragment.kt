package com.jimbonlemu.clefer.views.navbar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.FragmentArticleBinding
import com.jimbonlemu.clefer.databinding.FragmentProfileBinding
import com.jimbonlemu.clefer.views.article.SaveArticleActivity
import com.jimbonlemu.clefer.views.profile.AboutActivity


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //setup toolbar
        binding.toolbar.setupToolbar(
            title = getString(R.string.toolbar_profile),
            showBackButton = false,
        )
        mainButton()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mainButton() {
        binding.btnInfoApp.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }
    }
}