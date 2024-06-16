package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class CommentDiscussionResponse(

	@field:SerializedName("CommentDiscussionResponse")
	val commentDiscussionResponse: List<CommentDiscussionResponseItem>
)

data class CommentDiscussionResponseItem(

	@field:SerializedName("commentDate")
	val commentDate: String? = null,

	@field:SerializedName("commentBody")
	val commentBody: String? = null,

	@field:SerializedName("commentId")
	val commentId: Int? = null,

	@field:SerializedName("likerCount")
	val likerCount: Int? = null,

	@field:SerializedName("postId")
	val postId: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

)

data class LikeCommentResponse(
	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("commentLikeId")
	val commentLikeId: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("commentId")
	val commentId: Int? = null
)
