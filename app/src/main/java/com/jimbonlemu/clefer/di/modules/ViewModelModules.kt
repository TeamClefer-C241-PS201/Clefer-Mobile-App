package com.jimbonlemu.clefer.di.modules

import com.jimbonlemu.clefer.views.article.viewmodels.ArticleViewModel
import com.jimbonlemu.clefer.views.auth.viewmodels.AuthViewModels
import com.jimbonlemu.clefer.views.community.viewmodel.CommunityViewModel
import com.jimbonlemu.clefer.views.dashboard.viewmodels.HistoryViewModel
import com.jimbonlemu.clefer.views.dashboard.viewmodels.PredictViewModel
import com.jimbonlemu.clefer.views.dashboard.viewmodels.SliderViewModel
import com.jimbonlemu.clefer.views.profile.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { ArticleViewModel(get()) }
    viewModel { AuthViewModels(get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { PredictViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SliderViewModel() }
}