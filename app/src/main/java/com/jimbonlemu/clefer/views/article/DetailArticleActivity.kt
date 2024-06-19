package com.jimbonlemu.clefer.views.article

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreActivity
import com.jimbonlemu.clefer.databinding.ActivityDetailArticleBinding
import com.jimbonlemu.clefer.source.local.entity.FavoriteArticle
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.utils.Prefs
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleActivity : CoreActivity<ActivityDetailArticleBinding>() {
    private val detailArticleViewModel: ArticleViewModel by viewModel()
    private var isFavorite: Boolean = false
    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeData()
        setupToolbar(binding.mToolbar)
        setupFavoriteButton()
    }


    private fun observeData() {
        detailArticleViewModel.apply {
            val extraArticle = intent.getIntExtra(EXTRA_ARTICLE, 0)
            articleId = extraArticle
            getDetail(extraArticle)
            detail.observe(this@DetailArticleActivity) { article ->
                if (article != null) {
                    binding.tvTitleDetail.text = article.articleTitle
                    binding.tvDescDetail.text = article.articleDesc
                    Glide.with(this@DetailArticleActivity)
                        .load(article.articleImg)
                        .into(binding.ivItemPhotoDetail)
                    checkFavorite(Prefs.getUserId, articleId.toString())
                }
            }
        }
    }

    private fun setupFavoriteButton() {
        binding.btnSaveBookmarks.setOnClickListener {
            if (isFavorite) {
                deleteFavorite(articleId)
            } else {
                addFavorite(articleId)
            }
        }
    }

    private fun addFavorite(id: Int) {
        binding.apply {
            val favoriteArticle = FavoriteArticle(
                ownerId = Prefs.getUserId,
                id = id,
                articleTitle = tvTitleDetail.text.toString(),
                articleDesc = tvDescDetail.text.toString(),
                articleImg = detailArticleViewModel.detail.value?.articleImg ?: ""
            )
            detailArticleViewModel.insertFavoriteArticle(favoriteArticle)
            isFavorite = true
            updateFavoriteUI()
            CleferToast.successToast("Berhasil menambahkan ke favorit", this@DetailArticleActivity)
        }
    }

    private fun deleteFavorite(id: Int) {
        detailArticleViewModel.deleteFavorite(id)
        isFavorite = false
        updateFavoriteUI()
        CleferToast.successToast("Berhasil menghapus dari favorit", this)
    }

    private fun checkFavorite(ownerId: String, articleId: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val count = detailArticleViewModel.checkFavoriteById(ownerId, articleId)
            withContext(Dispatchers.Main) {
                isFavorite = count.toInt() == 1
                updateFavoriteUI()
            }
        }
    }

    private fun updateFavoriteUI() {
        val iconRes = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.btnSaveBookmarks.setImageResource(iconRes)
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override fun setupBinding(layoutInflater: LayoutInflater): ActivityDetailArticleBinding =
        ActivityDetailArticleBinding.inflate(layoutInflater)
}
