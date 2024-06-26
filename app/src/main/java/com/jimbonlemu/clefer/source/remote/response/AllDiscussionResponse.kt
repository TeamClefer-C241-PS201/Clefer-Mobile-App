package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class AllDiscussionResponse(

	@field:SerializedName("AllDiscussionResponse")
	val allDiscussionResponse: List<AllDiscussionResponseItem?>? = null
)

data class AllDiscussionResponseItem(

	@field:SerializedName("postDesc")
	val postDesc: String? = null,

	@field:SerializedName("postDate")
	val postDate: String? = null,

//	@field:SerializedName("LikerCount")
//	var likerCount: Int? = null,

	@field:SerializedName("likerCount")
	var likerCountById: Int? = null,

	@field:SerializedName("postTitle")
	val postTitle: String? = null,

	@field:SerializedName("postId")
	val postId: Int? = null,

	@field:SerializedName("postLike")
	val postLike: Int? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("commentCount")
	val commentCount: Int? = null,

	@field:SerializedName("likeStat")
	var likeStat: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,
)
