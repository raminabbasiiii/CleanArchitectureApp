package com.raminabbasiiii.cleanarchitectureapp.domain.model


data class Movie(
    val id: Int,
    val title: String,
    val poster: String,
    val year: String,
    val country: String,
    val rating: String,
    val genres: List<String>? = null,
    val images: List<String>? = null
)