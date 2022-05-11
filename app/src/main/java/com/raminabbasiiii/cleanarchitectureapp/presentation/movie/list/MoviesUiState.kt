package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.list

import androidx.paging.PagingData
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie

data class MoviesUiState(
    val query: String = "",
    val movies: PagingData<Movie>? = null,
)
