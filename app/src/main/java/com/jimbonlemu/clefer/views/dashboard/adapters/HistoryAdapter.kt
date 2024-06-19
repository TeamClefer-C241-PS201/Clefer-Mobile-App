package com.jimbonlemu.clefer.views.dashboard.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.databinding.ItemHistoryBinding
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed
import com.jimbonlemu.clefer.views.dashboard.AnalyzeActivity

class HistoryAdapter(
    private val listener: HistoryActionListener,
) : ListAdapter<HistoryAnalyzed, HistoryAdapter.ItemHistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHistoryViewHolder {
        return ItemHistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ItemHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemHistoryViewHolder(
        private val binding: ItemHistoryBinding,
        private val listener: HistoryActionListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyAnalyzed: HistoryAnalyzed) {
            historyAnalyzed.apply {
                with(binding) {
                    Glide.with(root).load(image.toUri()).into(ivHistory)
                    tvDiseaseResult.text = diseaseResult
                    tvDateScanned.text = date
                    root.setOnClickListener {
                        it.context.startActivity(
                            Intent(
                                it.context,
                                AnalyzeActivity::class.java
                            ).apply {
                                putExtra(AnalyzeActivity.PASSED_IMAGE, image)
                                putExtra(AnalyzeActivity.PASSED_RESULT, diseaseResult)
                                putExtra(AnalyzeActivity.PASSED_DESC, diseaseDesc)
                                putExtra(AnalyzeActivity.PASSED_SUGGESTION, diseaseSuggestion)
                                putExtra(AnalyzeActivity.IS_VIEWING_HISTORY, true)
                            })
                    }
                    btnDeleteHistory.setOnClickListener {
                        listener.onDeleteClicked(historyAnalyzed)
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryAnalyzed>() {
            override fun areItemsTheSame(
                oldItem: HistoryAnalyzed,
                newItem: HistoryAnalyzed,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: HistoryAnalyzed,
                newItem: HistoryAnalyzed,
            ): Boolean = oldItem == newItem
        }
    }

    interface HistoryActionListener {
        fun onDeleteClicked(historyAnalyzed: HistoryAnalyzed)
    }
}
