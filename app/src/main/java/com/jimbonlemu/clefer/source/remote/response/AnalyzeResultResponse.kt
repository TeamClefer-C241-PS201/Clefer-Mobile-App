package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class AnalyzeResultResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("articleTitle")
	val articleTitle: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("articleImg")
	val articleImg: String? = null,

	@field:SerializedName("articleDesc")
	val articleDesc: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
