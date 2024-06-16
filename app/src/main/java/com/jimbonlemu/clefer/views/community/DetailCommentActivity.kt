package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isGone
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
        setupPostCommentButton()
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

    private fun setupPostCommentButton() {
        binding.btnSendComment.setOnClickListener {
            val commentBody = binding.commentInput.text.toString().trim()
            if (commentBody.isNotEmpty()) {
                detailCommunityViewModel.createComment(postId, commentBody)
                binding.commentInput.text?.clear()
            } else {
                getToast("Please fill in the comment")
            }
        }
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

        detailCommunityViewModel.createComment.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Loading -> {
                    enabledComponent(false)
                }
                is ResponseState.Success -> {
                    enabledComponent(true)
                    getToast("Comment posted successfully")
                    detailCommunityViewModel.getCommentByPostId(postId)
                }
                is ResponseState.Error -> {
                    enabledComponent(true)
                    getToast(responseState.errorMessage)
                }
                else -> binding.main.isGone = true
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
                    binding.itemCommunity.btnLike.setImageResource(
                        if (discussion.likerCount == 0) R.drawable.ic_favorite_border else R.drawable.ic_favorite
                    )
                }
                is ResponseState.Error -> {
                    // Response error
                }
            }
        }
    }


    private fun enabledComponent(isComponentEnabled: Boolean) {
        binding.apply {
            if (isComponentEnabled) {
                commentInput.isEnabled = true
                btnSendComment.isEnabled = true
            } else {
                commentInput.isEnabled = false
                btnSendComment.isEnabled = false
            }
        }
    }

    private fun getToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailCommentBinding =
        ActivityDetailCommentBinding.inflate(layoutInflater)
}
