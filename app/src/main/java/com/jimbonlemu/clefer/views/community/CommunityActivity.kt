package com.jimbonlemu.clefer.views.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
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
        setupViews()
        observeData()
        setupButton()
    }

    private fun setupViews() {
        setupRecyclerView()
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        communityViewModel.getAllDiscussions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.mToolbar);
        binding.mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
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
                    }

                    is ResponseState.Success -> {
                        setShimmerEnable(false)
                        listCommunityAdapter.updateItems(response.data)
                    }

                    is ResponseState.Error -> {
                        setShimmerEnable(false)
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

    override fun onLikeButtonClicked(item: AllDiscussionResponseItem) {
        communityViewModel.likeDiscussion(item.postId!!)
    }

    private fun getToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityCommunityBinding =
        ActivityCommunityBinding.inflate(layoutInflater)
}
