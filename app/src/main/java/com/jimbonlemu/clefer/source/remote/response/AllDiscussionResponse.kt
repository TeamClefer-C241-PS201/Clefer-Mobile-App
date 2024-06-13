package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class AllDiscussionResponse(

	@field:SerializedName("AllDiscussionResponse")
	val allDiscussionResponse: List<AllDiscussionResponseItem>
)

data class AllDiscussionResponseItem(

	@field:SerializedName("postDesc")
	val postDesc: String,

	@field:SerializedName("postDate")
	val postDate: String,

	@field:SerializedName("likerCount")
	val likerCount: Int,

	@field:SerializedName("postTitle")
	val postTitle: String,

	@field:SerializedName("postId")
	val postId: Int,

	@field:SerializedName("postLike")
	val postLike: Int,

	@field:SerializedName("userId")
	val userId: Int
)
