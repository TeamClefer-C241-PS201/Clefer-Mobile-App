package com.jimbonlemu.clefer.views.community.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.ItemCommunityBinding
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.toTime
import com.jimbonlemu.clefer.views.community.DetailCommentActivity

class ListCommunityAdapter(private val onLikeClick: (Int, Boolean) -> Unit) :
    RecyclerView.Adapter<ListCommunityAdapter.ViewHolder>() {

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
                tvCommentCount.text = item.commentCount?.toString()
                btnLike.setOnClickListener {
                    item.isLiked = !item.isLiked
                    updateLikeIcon(item.isLiked)
                    val newLikerCount = item.likerCount?.let {
                        if (item.isLiked) it + 1 else it - 1
                    }
                    item.likerCount = newLikerCount
                    tvLikeCount.text = newLikerCount?.toString()
                    onLikeClick(item.postId!!, item.isLiked)
                }

                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailCommentActivity::class.java)
                    intent.putExtra(DetailCommentActivity.POST_ID, item.postId)
                    itemView.context.startActivity(intent)
                }
                updateLikeIcon(item.isLiked)
            }
        }

        private fun updateLikeIcon(isLiked: Boolean) {
            val likeIcon = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            itemBinding.btnLike.setImageResource(likeIcon)
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

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}



