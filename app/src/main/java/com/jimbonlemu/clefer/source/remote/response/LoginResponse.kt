package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName


data class LoginRequest(
	val email: String,
	val password: String
)
data class LoginResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: LoginResult? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
