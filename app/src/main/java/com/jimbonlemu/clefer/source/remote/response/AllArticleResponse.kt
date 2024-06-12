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
	val articleTitle: String? = null,

	@field:SerializedName("articleId")
	val articleId: Int? = null,

	@field:SerializedName("articleImg")
	val articleImg: String? = null,

	@field:SerializedName("articleDesc")
	val articleDesc: String? = null
)
