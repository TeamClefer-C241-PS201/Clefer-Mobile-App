package com.jimbonlemu.clefer.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
	val name: String,
	val email: String,
	val username: String,
	val password: String
)

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: RegisterResult? = null
)

data class RegisterResult(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
