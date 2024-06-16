package com.jimbonlemu.clefer.views.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.community.adapter.ListCommunityAdapter
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
class CommunityActivity : CoreActivity<ActivityCommunityBinding>(), ListCommunityAdapter.OnLikeButtonClickListener {

    private val communityViewModel: CommunityViewModel by viewModel()
    private val listCommunityAdapter = ListCommunityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
        setupButton()
    }

    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
    }

    @Suppress("DEPRECATION")
    private fun setupToolbar() {
        binding.toolbar.setupToolbar(
            title = getString(R.string.community),
            showBackButton = true,
            backAction = { onBackPressed() }
        )
    }

    private fun setupButton() {
        binding.apply {
            btnPost.setOnClickListener {
                startActivity(Intent(this@CommunityActivity, QuestionActivity::class.java))
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = listCommunityAdapter
        communityViewModel.getAllDiscussions()
    }

    private fun observeData() {
        communityViewModel.getAllDiscussions.observe(this) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    // Handle loading
                }

                is ResponseState.Success -> {
                    listCommunityAdapter.updateItems(response.data)
                }

                is ResponseState.Error -> {
                    // Handle error
                }
            }
        }

        communityViewModel.likeDiscussionState.observe(this) { response ->
            when (response) {
                is ResponseState.Loading -> {
                    // Handle loading
                }

                is ResponseState.Success -> {
                    // Handle success
                }

                is ResponseState.Error -> {
                    // Handle error
                }
            }
        }
    }

    override fun onLikeButtonClicked(item: AllDiscussionResponseItem) {
        communityViewModel.likeDiscussion(item.postId!!)
    }


    private fun getToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCommunityBinding =
        ActivityCommunityBinding.inflate(layoutInflater)
}



