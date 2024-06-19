package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityHistoryBinding
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.dashboard.adapters.HistoryAdapter
import com.jimbonlemu.clefer.views.dashboard.viewmodels.HistoryViewModel
import org.koin.android.ext.android.inject

class HistoryActivity : CoreActivity<ActivityHistoryBinding>(), HistoryAdapter.HistoryActionListener {

    private val historyViewModel: HistoryViewModel by inject()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupToolbar(mToolbar)
            loadHistoryData()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityHistoryBinding =
        ActivityHistoryBinding.inflate(layoutInflater)

    private fun ActivityHistoryBinding.loadHistoryData() {
        shimmerHistory.visibility = View.VISIBLE
        shimmerHistory.startShimmer()
        historyAdapter = HistoryAdapter(this@HistoryActivity)
        rvHistoryAnalyzed.layoutManager = LinearLayoutManager(this@HistoryActivity)
        rvHistoryAnalyzed.adapter = historyAdapter

        historyViewModel.apply {
            getAllHistoryByOwner(Prefs.getUserId).observe(this@HistoryActivity) { historyList ->
                if (historyList.isNullOrEmpty()) {
                    emptyLayout.root.visibility = View.VISIBLE
                    rvHistoryAnalyzed.visibility = View.GONE
                } else {
                    emptyLayout.root.visibility = View.GONE
                    rvHistoryAnalyzed.visibility = View.VISIBLE
                    historyAdapter.submitList(historyList.reversed())
                }
                stopShimmer()
            }
        }
    }

    private fun ActivityHistoryBinding.stopShimmer() {
        shimmerHistory.stopShimmer()
        shimmerHistory.visibility = View.GONE
    }

    override fun onDeleteClicked(historyAnalyzed: HistoryAnalyzed) {
        historyViewModel.deleteHistoryAnalyzed(historyAnalyzed.id.toString())
    }
}