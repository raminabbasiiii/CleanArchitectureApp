package com.raminabbasiiii.cleanarchitectureapp.data.network.response

import com.google.gson.annotations.SerializedName
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.MetaDataDto
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.movie.MovieDto

data class MovieListResponse(
    @SerializedName("data")
    val movies: List<MovieDto>,

    @SerializedName("metadata")
    val metaData: MetaDataDto
    )