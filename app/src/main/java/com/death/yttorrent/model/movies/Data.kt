package com.death.yttorrent.model.movies

import com.google.gson.annotations.SerializedName

data class Data(

		@field:SerializedName("movies")
	val movies: List<MoviesItem?>? = null,

		@field:SerializedName("page_number")
	val pageNumber: Int? = null,

		@field:SerializedName("movie_count")
	val movieCount: Int? = null,

		@field:SerializedName("limit")
	val limit: Int? = null
)