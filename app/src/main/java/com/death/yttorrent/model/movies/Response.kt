package com.death.yttorrent.model.movies

import com.google.gson.annotations.SerializedName

data class Response(

		@field:SerializedName("status_message")
	val statusMessage: String? = null,

		@field:SerializedName("data")
	val data: Data? = null,

		@field:SerializedName("status")
	val status: String? = null
)