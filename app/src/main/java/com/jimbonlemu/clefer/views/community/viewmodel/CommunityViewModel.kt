package com.jimbonlemu.clefer.views.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CreateDiscussionResponse
import com.jimbonlemu.clefer.utils.ResponseState
import kotlinx.coroutines.launch

class CommunityViewModel(private val repository: AppRepository) : ViewModel() {

    private val _createDiscussion = MutableLiveData<ResponseState<CreateDiscussionResponse>>()
    val createDiscussion: LiveData<ResponseState<CreateDiscussionResponse>> by lazy { _createDiscussion }

    private val _getAllDiscussions =
        MutableLiveData<ResponseState<List<AllDiscussionResponseItem>>>()
    val getAllDiscussions: LiveData<ResponseState<List<AllDiscussionResponseItem>>> by lazy { _getAllDiscussions }

    private val _getDiscussionById = MutableLiveData<ResponseState<AllDiscussionResponseItem>>()
    val getDiscussionById: LiveData<ResponseState<AllDiscussionResponseItem>> by lazy { _getDiscussionById }

    private val _getCommentById = MutableLiveData<ResponseState<List<CommentDiscussionResponseItem>>>()
    val getCommentById: LiveData<ResponseState<List<CommentDiscussionResponseItem>>> by lazy { _getCommentById }

    fun getAllDiscussions() {
        viewModelScope.launch {
            repository.getAllDiscussion().collect {
                _getAllDiscussions.value = it
            }
        }
    }

    fun createDiscussion(discussionRequest: DiscussionRequest) {
        viewModelScope.launch {
            repository.createDiscussion(discussionRequest).collect {
                _createDiscussion.value = it
            }
        }
    }

    fun getDiscussionById(postId: Int) {
        viewModelScope.launch {
            repository.getDiscussionById(postId).collect {
                _getDiscussionById.value = it
            }
        }
    }

    fun getCommentByPostId(postId: Int) {
        viewModelScope.launch {
            repository.getCommentById(postId).collect {
                _getCommentById.value = it
            }
        }
    }
}