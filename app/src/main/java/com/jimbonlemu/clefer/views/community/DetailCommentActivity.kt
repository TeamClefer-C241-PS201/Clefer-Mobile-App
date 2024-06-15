package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailCommentBinding
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.community.adapter.ListCommentAdapter
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCommentActivity : CoreActivity<ActivityDetailCommentBinding>() {
    private val detailCommunityViewModel: CommunityViewModel by viewModel()
    private val listCommentAdapter = ListCommentAdapter()
    private var postId: Int = 0

    companion object {
        const val POST_ID = "post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
        observeComments()
    }

    private fun setupViews() {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.toolbar.setupToolbar(
            title = getString(R.string.comment_detail),
            showBackButton = true,
            backAction = { onBackPressedDispatcher.onBackPressed() }
        )
    }

    private fun setupRecyclerView() {
        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.adapter = listCommentAdapter
        postId = intent.getIntExtra(POST_ID, 0)
        detailCommunityViewModel.getCommentByPostId(postId)
    }

    private fun observeComments() {
        detailCommunityViewModel.getCommentById.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Loading -> {
                    // Response loading
                }
                is ResponseState.Success -> {
                    listCommentAdapter.updateItems(responseState.data)
                }
                is ResponseState.Error -> {
                    // Response error
                }
            }
        }
    }

    private fun observeData() {
        postId = intent.getIntExtra(POST_ID, 0)
        detailCommunityViewModel.getDiscussionById(postId)
        detailCommunityViewModel.getDiscussionById.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Loading -> {
                    // Response loading
                }
                is ResponseState.Success -> {
                    val discussion = responseState.data
                    binding.itemCommunity.tvName.text = discussion.postTitle
                    binding.itemCommunity.tvItemDesc.text = discussion.postDesc
                    binding.itemCommunity.tvDate.text = discussion.postDate
                    binding.itemCommunity.tvLikeCount.text = discussion.likerCount.toString()
                    binding.itemCommunity.tvCommentCount.text = discussion.commentCount.toString()
                }
                is ResponseState.Error -> {
                    // Response error
                }
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailCommentBinding =
        ActivityDetailCommentBinding.inflate(layoutInflater)
}
