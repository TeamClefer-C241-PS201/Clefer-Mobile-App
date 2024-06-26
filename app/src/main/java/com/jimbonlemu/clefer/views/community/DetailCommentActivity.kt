package com.jimbonlemu.clefer.views.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailCommentBinding
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.utils.toTime
import com.jimbonlemu.clefer.views.community.adapter.ListCommentAdapter
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCommentActivity : CoreActivity<ActivityDetailCommentBinding>(),
    ListCommentAdapter.OnCommentLikeButtonClickListener {
    private val detailCommunityViewModel: CommunityViewModel by viewModel()
    private val listCommentAdapter = ListCommentAdapter(this)
    private var postId: Int = 0

    companion object {
        const val POST_ID = "post_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        setupToolbar(binding.mToolbar)
        observeData()
        observeComments()
        setupPostCommentButton()
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
                CleferToast.informToast("Harap isi komentar terlebih dahulu", this)
            }
        }
    }

    private fun observeComments() {
        detailCommunityViewModel.getCommentById.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Loading -> {
                    setShimmerEnable(true)
                    isEnableEmptyLayout(false)
                }
                is ResponseState.Success -> {
                    val comments = responseState.data
                    listCommentAdapter.updateItems(comments)
                    setShimmerEnable(false)
                    if (comments.isEmpty()) {
                        isEnableEmptyLayout(true)
                    } else {
                        isEnableEmptyLayout(false)
                    }
                    CleferToast.successToast("Berhasil memuat komentar", this)
                }

                is ResponseState.Error -> {
                    setShimmerEnable(false)
                    isEnableEmptyLayout(true)
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
                    detailCommunityViewModel.getCommentByPostId(postId)
                    CleferToast.successToast("Komentar berhasil dibuat", this)
                }

                is ResponseState.Error -> {
                    enabledComponent(true)
                    CleferToast.errorToast("Komentar gagal dibuat", this)

                }

                else -> binding.main.isGone = true
            }
        }

        detailCommunityViewModel.likeCommentState.observe(this) { responseState ->
            when (responseState) {
                is ResponseState.Loading -> {
                    // response loading
                }

                is ResponseState.Success -> {
                    //response success
                }

                is ResponseState.Error -> {
                    // response error
                }
            }
        }
    }

    private fun setShimmerEnable(shimmerEnable: Boolean) {
        binding.apply {
            if (shimmerEnable) {
                shimmerLayout.apply {
                    visibility = View.VISIBLE
                    startShimmer()
                }
                rvComments.visibility = View.INVISIBLE
            } else {
                shimmerLayout.apply {
                    visibility = View.GONE
                    startShimmer()
                }
                rvComments.visibility = View.VISIBLE
            }
        }
    }

    override fun onCommentLikeButtonClicked(postId: Int, commentId: Int) {
        detailCommunityViewModel.likeComment(postId, commentId)
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
                    binding.itemCommunity.tvName.text = discussion.name
                    binding.itemCommunity.tvItemTitle.text = discussion.postTitle
                    binding.itemCommunity.tvItemDesc.text = discussion.postDesc
                    binding.itemCommunity.tvDate.text = discussion.postDate?.toTime()
                    binding.itemCommunity.tvLikeCount.text = discussion.likerCountById.toString()
                    binding.itemCommunity.tvCommentCount.text = discussion.commentCount.toString()
                    binding.itemCommunity.btnLike.setImageResource(
                        if (discussion.likerCountById == 0) R.drawable.ic_favorite_border else R.drawable.ic_favorite
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

    private fun isEnableEmptyLayout(isEnable: Boolean) {
        binding.apply {
            if (isEnable) {
                emptyCommentLayout.apply {
                    root.visibility = View.VISIBLE
                    tvEmptyTitle.text = "Belum ada postingan komentar"
                }
                rvComments.visibility = View.INVISIBLE

            } else {
                emptyCommentLayout.apply {
                    root.visibility = View.GONE
                    tvEmptyTitle.text = getString(R.string.title_empty_data)
                }
                rvComments.visibility = View.VISIBLE
            }
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailCommentBinding =
        ActivityDetailCommentBinding.inflate(layoutInflater)
}
