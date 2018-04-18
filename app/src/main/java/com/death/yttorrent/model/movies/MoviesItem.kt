package com.death.yttorrent.model.movies

import com.google.gson.annotations.SerializedName

data class MoviesItem(

		@field:SerializedName("small_cover_image")
	val smallCoverImage: String? = null,

		@field:SerializedName("year")
	val year: Int? = null,

		@field:SerializedName("description_full")
	val descriptionFull: String? = null,

		@field:SerializedName("rating")
	val rating: Double? = null,

		@field:SerializedName("large_cover_image")
	val largeCoverImage: String? = null,

		@field:SerializedName("title_long")
	val titleLong: String? = null,

		@field:SerializedName("language")
	val language: String? = null,

		@field:SerializedName("yt_trailer_code")
	val ytTrailerCode: String? = null,

		@field:SerializedName("title")
	val title: String? = null,

		@field:SerializedName("mpa_rating")
	val mpaRating: String? = null,

		@field:SerializedName("title_english")
	val titleEnglish: String? = null,

		@field:SerializedName("id")
	val id: Int? = null,

		@field:SerializedName("state")
	val state: String? = null,

		@field:SerializedName("slug")
	val slug: String? = null,

		@field:SerializedName("summary")
	val summary: String? = null,

		@field:SerializedName("date_uploaded")
	val dateUploaded: String? = null,

		@field:SerializedName("runtime")
	val runtime: Int? = null,

		@field:SerializedName("synopsis")
	val synopsis: String? = null,

		@field:SerializedName("url")
	val url: String? = null,

		@field:SerializedName("imdb_code")
	val imdbCode: String? = null,

		@field:SerializedName("background_image")
	val backgroundImage: String? = null,

		@field:SerializedName("torrents")
	val torrents: List<TorrentsItem?>? = null,

		@field:SerializedName("date_uploaded_unix")
	val dateUploadedUnix: Int? = null,

		@field:SerializedName("background_image_original")
	val backgroundImageOriginal: String? = null,

		@field:SerializedName("medium_cover_image")
	val mediumCoverImage: String? = null
)