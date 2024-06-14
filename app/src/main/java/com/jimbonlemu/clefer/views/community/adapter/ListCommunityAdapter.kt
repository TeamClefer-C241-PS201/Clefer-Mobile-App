package com.jimbonlemu.clefer.views.community.adapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.databinding.ItemCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime

class ListCommunityAdapter : RecyclerView.Adapter<ListCommunityAdapter.ViewHolder>() {

    private var items: List<AllDiscussionResponseItem> = emptyList()

    inner class ViewHolder(private val itemBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: AllDiscussionResponseItem) {
            itemBinding.apply {
                tvName.text = item.postTitle ?: "No Title"
                tvItemDesc.text = item.postDesc ?: "No Description"
                tvDate.text = item.postDate?.toTime() ?: "No Date"
                tvLikeCount.text = item.postLike?.toString() ?: "0"
                tvCommentCount.text = item.likerCount?.toString() ?: "0"

                // Example of handling like button click
                ivLike.setOnClickListener {
                    // handleLikeClick(item)
                }
            }
        }
    }

    fun updateItems(newItems: List<AllDiscussionResponseItem>) {
        items = newItems
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

