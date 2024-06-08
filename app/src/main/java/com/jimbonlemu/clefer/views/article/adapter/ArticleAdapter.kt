package com.jimbonlemu.clefer.views.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.databinding.ItemArticleBinding
import com.jimbonlemu.clefer.source.remote.response.ArticlesItem

class ArticleAdapter :
    PagingDataAdapter<ArticlesItem, ArticleAdapter.ArticleViewHolder>(LIST_ARTICLE_DIFF_CALLBACK) {
    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleItem: ArticlesItem) {
            binding.apply {
                tvTitle.text = articleItem.title
                tvItemDesc.text = articleItem.description
                Glide.with(root)
                    .load(articleItem.urlToImage)
                    .into(ivItemPhoto)
            }
//            binding.ItemStory.setOnClickListener {
//                val intent = Intent(it.context, DetailArticleActivity::class.java)
//                intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, articleItem)
//                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    companion object {
        val LIST_ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}