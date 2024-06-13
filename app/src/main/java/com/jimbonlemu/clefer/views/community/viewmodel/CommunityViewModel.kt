package com.jimbonlemu.clefer.views.community.viewmodel

import androidx.lifecycle.ViewModel
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest

class CommunityViewModel (private val repository: AppRepository) : ViewModel() {
    fun getAllDiscussions() = repository.getAllDiscussion()
    fun createDiscussion(discussionRequest: DiscussionRequest) = repository.createDiscussion(discussionRequest)
}