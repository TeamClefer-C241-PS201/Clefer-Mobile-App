package com.jimbonlemu.clefer.views.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentArticleBinding
import com.jimbonlemu.clefer.utils.CleferToast
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : CoreFragment<FragmentArticleBinding>() {
    private val articleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter(ArticleAdapter.AdapterType.PAGING)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupMainButton()
        observeData()
    }


    private fun setupRecyclerView() {
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireContext())
            rvArticle.adapter = articleAdapter
        }
    }

    private fun setupMainButton() {
        binding.btnBookmark.setOnClickListener {
            startActivity(Intent(requireContext(), SaveArticleActivity::class.java))
        }
    }

    private fun observeData() {
        binding.apply {
            shimmerLayout.visibility = View.VISIBLE
            articleViewModel.getAllArticles.observe(viewLifecycleOwner) { pagingData ->
                lifecycleScope.launch {
                    articleAdapter.submitData(lifecycle, pagingData)
                    shimmerLayout.visibility = View.GONE
                    val isEmpty = articleAdapter.itemCount == 0
                    if (isEmpty) {
                        CleferToast.successToast("Berhasil Memuat Artikel", requireContext())
                    } else {
                        CleferToast.errorToast("Gagal Memuat Artikel", requireContext())
                    }
                }
            }
        }
    }

    private fun isEmptyLayoutArticleEnable(isEnable: Boolean) {
        binding.apply {
            emptyLayoutArticle.apply {
                if (isEnable) {
                    root.visibility = View.VISIBLE
                    tvEmptyTitle.text = getString(R.string.title_no_posted_articles)
                } else {
                    root.visibility = View.GONE
                }
            }
        }
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)

}
