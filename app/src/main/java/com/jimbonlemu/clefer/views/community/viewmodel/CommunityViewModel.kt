package com.jimbonlemu.clefer.views.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.utils.ResponseState
import kotlinx.coroutines.launch

class CommunityViewModel (private val repository: AppRepository) : ViewModel() {

    private val _createDiscussion = MutableLiveData<ResponseState<AllDiscussionResponse>>()
    val createDiscussion: LiveData<ResponseState<AllDiscussionResponse>> by lazy { _createDiscussion}

    private val _getAllDiscussions = MutableLiveData<ResponseState<List<AllDiscussionResponseItem>>>()
    val getAllDiscussions: LiveData<ResponseState<List<AllDiscussionResponseItem>>> by lazy { _getAllDiscussions }

    fun getAllDiscussions() {
        viewModelScope.launch {
            repository.getAllDiscussion().collect { responseState ->
                _getAllDiscussions.value = responseState
            }
        }
    }

    fun createDiscussion(discussionRequest: DiscussionRequest) {
        viewModelScope.launch {
            repository.createDiscussion(discussionRequest).collect{
                _createDiscussion.value = it
            }
        }
    }
}