package com.jimbonlemu.clefer.views.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbonlemu.clefer.repository.AppRepository
import com.jimbonlemu.clefer.source.remote.request.CommentRequest
import com.jimbonlemu.clefer.source.remote.request.DiscussionRequest
import com.jimbonlemu.clefer.source.remote.response.AllDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CommentDiscussionResponseItem
import com.jimbonlemu.clefer.source.remote.response.CreateDiscussionResponse
import com.jimbonlemu.clefer.source.remote.response.LikeCommentResponse
import com.jimbonlemu.clefer.source.remote.response.LikeDiscussionResponse
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

    private val _getCommentById =
        MutableLiveData<ResponseState<List<CommentDiscussionResponseItem>>>()
    val getCommentById: LiveData<ResponseState<List<CommentDiscussionResponseItem>>> by lazy { _getCommentById }

    private val _createComment = MutableLiveData<ResponseState<CommentDiscussionResponseItem>>()
    val createComment: LiveData<ResponseState<CommentDiscussionResponseItem>> by lazy { _createComment }

    private val _likeDiscussionState = MutableLiveData<ResponseState<LikeDiscussionResponse>>()
    val likeDiscussionState: LiveData<ResponseState<LikeDiscussionResponse>> = _likeDiscussionState

    private val _likeCommentState = MutableLiveData<ResponseState<LikeCommentResponse>>()
    val likeCommentState: LiveData<ResponseState<LikeCommentResponse>> = _likeCommentState

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

    fun createComment(postId: Int, commentBody: String) {
        viewModelScope.launch {
            val commentRequest = CommentRequest(commentBody)
            repository.createCommentById(postId, commentRequest).collect {
                _createComment.value = it
            }
        }
    }

    fun likeDiscussion(postId: Int) {
        viewModelScope.launch {
            repository.likeDiscussion(postId).collect {
                _likeDiscussionState.value = it
            }
        }
    }

    fun likeComment(postId: Int, commentId: Int) {
        viewModelScope.launch {
            repository.likeComment(postId, commentId).collect {
                _likeCommentState.value = it
            }
        }
    }


}