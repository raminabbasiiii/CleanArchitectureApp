package com.raminabbasiiii.cleanarchitectureapp.data.network.dto.moviedetail

import com.google.gson.annotations.SerializedName

class MovieDetailDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster")
    val poster: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("imdb_rating")
    val rating: String,

    @SerializedName("rated")
    val rated: String,

    @SerializedName("released")
    val released: String,

    @SerializedName("runtime")
    val runtime: String,

    @SerializedName("director")
    val director: String,

    @SerializedName("writer")
    val writer: String,

    @SerializedName("actors")
    val actors: String,

    @SerializedName("plot")
    val plot: String,

    @SerializedName("awards")
    val awards: String,

    @SerializedName("imdb_votes")
    val votes: String,

    @SerializedName("genres")
    val genres: List<String>? = null,

    @SerializedName("images")
    val images: List<String>? = null
)