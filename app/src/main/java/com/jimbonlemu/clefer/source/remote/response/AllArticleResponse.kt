package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class AllArticleResponse(

	@field:SerializedName("data")
	val data: List<List<DataItemItem>>,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemItem(

	@field:SerializedName("articleTitle")
	val articleTitle: String,

	@field:SerializedName("articleId")
	val articleId: Int,

	@field:SerializedName("articleImg")
	val articleImg: String,

	@field:SerializedName("articleDesc")
	val articleDesc: String
)
