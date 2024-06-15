package com.jimbonlemu.clefer.source.remote.request

data class DiscussionRequest(
    val postTitle: String,
    val postDesc: String
)
data class CommentRequest(
    val commentBody: String
)
