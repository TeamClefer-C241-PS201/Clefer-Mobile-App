package com.jimbonlemu.clefer.views.community.adapter
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.utils.toTime

class ListCommunityAdapter : RecyclerView.Adapter<ListCommunityAdapter.ViewHolder>() {

    private var items: List<AllDiscussionResponseItem> = emptyList()

    inner class ViewHolder(private val itemBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllDiscussionResponseItem) {
            itemBinding.apply {
                tvName.text = item.postTitle
                tvItemDesc.text = item.postDesc
                tvDate.text = item.postDate?.toTime()
                tvLikeCount.text = item.likerCount?.toString()
                ivLike.setOnClickListener {

                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<AllDiscussionResponseItem>) {
        items = newItems.sortedByDescending { it.postDate }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommunityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

