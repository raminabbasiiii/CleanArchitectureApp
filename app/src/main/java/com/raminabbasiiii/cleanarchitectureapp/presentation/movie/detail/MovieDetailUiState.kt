package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.detail

import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val errorMessage: String? = null
)
