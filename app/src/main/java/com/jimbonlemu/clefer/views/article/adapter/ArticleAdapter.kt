package com.jimbonlemu.clefer.views.article.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.databinding.ItemArticleBinding
import com.jimbonlemu.clefer.databinding.ItemSaveArticleBinding
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.source.remote.response.DataItemItem
import com.jimbonlemu.clefer.views.article.DetailArticleActivity

class ArticleAdapter(
    private val type: AdapterType,
) :
    PagingDataAdapter<DataItemItem, RecyclerView.ViewHolder>(LIST_ARTICLE_DIFF_CALLBACK) {

    enum class AdapterType {
        PAGING, FAVORITE
    }

    private var favoriteArticles = listOf<FavoriteArticle>()

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleItem: DataItemItem?) {
            articleItem ?: return // Handle null items if any

            binding.apply {
                tvTitle.text = articleItem.articleTitle
                tvItemDesc.text = articleItem.articleDesc
                Glide.with(root)
                    .load(articleItem.articleImg)
                    .into(ivItemPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailArticleActivity::class.java)
                    intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, articleItem.articleId)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    inner class FavoriteArticleViewHolder(private val binding: ItemSaveArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(article: FavoriteArticle) {
            binding.apply {
                tvTitle.text = article.articleTitle
                Glide.with(root)
                    .load(article.articleImg)
                    .into(ivItemPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailArticleActivity::class.java)
                    intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, article.id)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            AdapterType.PAGING -> {
                val binding =
                    ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ArticleViewHolder(binding)
            }

            AdapterType.FAVORITE -> {
                val binding = ItemSaveArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                FavoriteArticleViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            AdapterType.PAGING -> (holder as ArticleViewHolder).bind(getItem(position))
            AdapterType.FAVORITE -> (holder as FavoriteArticleViewHolder).bind(favoriteArticles[position])
        }
    }

    override fun getItemCount(): Int {
        return when (type) {
            AdapterType.PAGING -> super.getItemCount()
            AdapterType.FAVORITE -> favoriteArticles.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteArticles(articles: List<FavoriteArticle>) {
        favoriteArticles = articles
        notifyDataSetChanged()
    }

    companion object {
        private val LIST_ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemItem>() {
            override fun areItemsTheSame(oldItem: DataItemItem, newItem: DataItemItem): Boolean {
                return oldItem.articleId == newItem.articleId
            }

            override fun areContentsTheSame(oldItem: DataItemItem, newItem: DataItemItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
