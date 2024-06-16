package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class CreateDiscussionResponse(

	@field:SerializedName("postDesc")
	val postDesc: String? = null,

	@field:SerializedName("postTitle")
	val postTitle: String? = null,

	@field:SerializedName("postId")
	val postId: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)

data class LikeDiscussionResponse(
	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("postLikeId")
	val postLikeId: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("commentId")
	val commentId: Int? = null)
