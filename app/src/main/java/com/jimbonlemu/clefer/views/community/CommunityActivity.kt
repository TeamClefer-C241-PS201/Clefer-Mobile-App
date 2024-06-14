package com.jimbonlemu.clefer.views.community
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCommunityBinding
import com.jimbonlemu.clefer.utils.ResponseState
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
        communityViewModel.getAllDiscussions() // Panggil fungsi untuk memuat diskusi
    }

    private fun setupViews() {
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
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = listCommunityAdapter
    }

    private fun observeData() {
        communityViewModel.getAllDiscussions.observe(this) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    //comment
                }
                is ResponseState.Success -> {
                    response.data.forEach { item ->
                        //comment

                    }
                    listCommunityAdapter.updateItems(response.data)
                }
                is ResponseState.Error -> {
                    //comment
                }
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCommunityBinding =
        ActivityCommunityBinding.inflate(layoutInflater)
}

