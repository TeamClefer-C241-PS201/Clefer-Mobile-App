package com.jimbonlemu.clefer.views.article

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.core.CoreFragment
import com.jimbonlemu.clefer.databinding.FragmentArticleBinding
import com.jimbonlemu.clefer.views.article.adapter.ArticleAdapter
import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : CoreFragment<FragmentArticleBinding>() {
    private val articleViewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter(ArticleAdapter.AdapterType.PAGING)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupMainButton()
        observeData()
    }

    private fun setupToolbar() {
        binding.toolbar.setupToolbar(
            title = getString(R.string.title_article),
            showBackButton = false
        )
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireContext())
            rvArticle.adapter = articleAdapter
        }
    }

    private fun setupView() {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupMainButton() {
        binding.btnBookmark.setOnClickListener {
            startActivity(Intent(requireContext(), SaveArticleActivity::class.java))
        }
    }

    private fun observeData() {
        articleViewModel.getAllArticles.observe(viewLifecycleOwner) { pagingData ->
            articleAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun setupFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)

}
