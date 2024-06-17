package com.jimbonlemu.clefer.views.dashboard.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.databinding.ItemHistoryBinding
import com.jimbonlemu.clefer.source.local.entity.HistoryAnalyzed


class HistoryAdapter(
    private var listHistoryAnalyzed: List<HistoryAnalyzed>,
    private val context: Context,
    private val actionListener: HistoryActionListener
) : RecyclerView.Adapter<HistoryAdapter.ItemHistoryViewHolder>() {
    class ItemHistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHistoryViewHolder {
        return ItemHistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listHistoryAnalyzed.size

    override fun onBindViewHolder(holder: ItemHistoryViewHolder, position: Int) {
        listHistoryAnalyzed.reversed()[position].apply {
            with(holder.binding) {
                Glide.with(root).load(image.toUri()).into(ivHistory)
                with(context.resources) {
                    tvDiseaseResult.text = diseaseResult
                    tvDateScanned.text = date
                }
                btnDeleteHistory.setOnClickListener {
                    actionListener.onDeleteClicked(this@apply)
                }
            }
        }
    }

    interface HistoryActionListener {
        fun onDeleteClicked(historyAnalyzed: HistoryAnalyzed)
    }
}