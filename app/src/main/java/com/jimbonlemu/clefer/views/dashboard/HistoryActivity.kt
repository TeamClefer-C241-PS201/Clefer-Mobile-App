package com.jimbonlemu.clefer.views.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityHistoryBinding
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.dashboard.adapters.HistoryAdapter
import com.jimbonlemu.clefer.views.dashboard.viewmodels.HistoryViewModel
import org.koin.android.ext.android.inject

class HistoryActivity : CoreActivity<ActivityHistoryBinding>() {

    private val historyViewModel: HistoryViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            setupToolbar()
            loadHistoryData()
        }
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityHistoryBinding =
        ActivityHistoryBinding.inflate(layoutInflater)

    private fun ActivityHistoryBinding.loadHistoryData() {
        historyViewModel.apply {
            getAllHistoryByOwner(Prefs.getUserId)
                .observe(this@HistoryActivity) {
                    rvHistoryAnalyzed.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    rvHistoryAnalyzed.adapter = HistoryAdapter(
                        it,
                        this@HistoryActivity,
                        (object :
                            HistoryAdapter.HistoryActionListener {
                            override fun onDeleteClicked(historyAnalyzed: HistoryAnalyzed) {
                                deleteHistoryAnalyzed(historyAnalyzed.id.toString())
                            }

                        })
                    )
                }
        }
    }


    private fun ActivityHistoryBinding.setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back);
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}