package com.jimbonlemu.clefer.views.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.ResponseState
import com.jimbonlemu.clefer.views.community.adapter.ListCommunityAdapter
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import org.koin.android.ext.android.inject

class CommunityActivity : CoreActivity<ActivityCommunityBinding>(),
    ListCommunityAdapter.OnLikeButtonClickListener {

    private val communityViewModel: CommunityViewModel by inject()
    private val listCommunityAdapter = ListCommunityAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
        setupToolbar(binding.mToolbar)

        observeData()
        setupButton()
    }

    override fun onResume() {
        super.onResume()
        communityViewModel.getAllDiscussions()
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
    }

    private fun observeData() {
        binding.apply {
            communityViewModel.getAllDiscussions.observe(this@CommunityActivity) { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        setShimmerEnable(true)
                        isEnableEmptyLayout(false)
                    }

                    is ResponseState.Success -> {
                        setShimmerEnable(false)
                        if (response.data.isEmpty()) {
                            isEnableEmptyLayout(true)
                        } else {
                            isEnableEmptyLayout(false)
                        }
                        listCommunityAdapter.updateItems(response.data)
                        CleferToast.successToast("Berhasil memuat data", this@CommunityActivity)
                    }

                    is ResponseState.Error -> {
                        setShimmerEnable(false)
                        isEnableEmptyLayout(false)
                        CleferToast.errorToast("Gagal memuat data", this@CommunityActivity)
                    }
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
                rvItems.visibility = View.INVISIBLE
            } else {
                shimmerLayout.apply {
                    visibility = View.GONE
                    startShimmer()
                }
                rvItems.visibility = View.VISIBLE
            }
        }
    }

    private fun isEnableEmptyLayout(isEnable: Boolean) {
        binding.apply {
            if (isEnable) {
                emptyLayoutCommunity.apply {
                    root.visibility = View.VISIBLE
                    tvEmptyTitle.text = "Belum ada postingan pertanyaan"
                }

            } else {
                emptyLayoutCommunity.apply {
                    root.visibility = View.GONE
                    tvEmptyTitle.text = getString(R.string.title_empty_data)
                }
            }
        }
    }

    override fun onLikeButtonClicked(item: AllDiscussionResponseItem) {
        communityViewModel.likeDiscussion(item.postId!!)
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCommunityBinding =
        ActivityCommunityBinding.inflate(layoutInflater)
}
