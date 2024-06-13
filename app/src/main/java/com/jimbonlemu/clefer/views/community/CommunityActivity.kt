package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCommunityBinding
import com.jimbonlemu.clefer.views.community.adapter.ListCommunityAdapter
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommunityActivity : CoreActivity<ActivityCommunityBinding>() {
    private val communityViewModel: CommunityViewModel by viewModel()
    private val listCommunityAdapter = ListCommunityAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()


    }
    private fun setupViews(){
        setupToolbar()
        setupRecyclerView()

    }
    private fun setupToolbar() {
        binding.toolbar.setupToolbar(
            title = getString(R.string.community),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }
    private fun setupRecyclerView() {
        binding.rvItems.adapter = listCommunityAdapter

    }
    private fun observeData() {
    }


    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCommunityBinding =
        ActivityCommunityBinding.inflate(layoutInflater)
}