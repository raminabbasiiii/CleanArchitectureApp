package com.raminabbasiiii.cleanarchitectureapp.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val country: String,
    val rating: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val director: String,
    val writer: String,
    val actors: String,
    val plot: String,
    val awards: String,
    val votes: String,
    val genres: List<String>? = null,
    val images: List<String>? = null
)
