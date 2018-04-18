package com.death.yttorrent.model.movies

import com.google.gson.annotations.SerializedName

data class TorrentsItem(

	@field:SerializedName("size_bytes")
	val sizeBytes: Long? = null,

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("seeds")
	val seeds: Long? = null,

	@field:SerializedName("date_uploaded")
	val dateUploaded: String? = null,

	@field:SerializedName("peers")
	val peers: Long? = null,

	@field:SerializedName("date_uploaded_unix")
	val dateUploadedUnix: Long? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("hash")
	val hash: String? = null,

	@field:SerializedName("quality")
	val quality: String? = null
)