package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.detail

sealed class MovieDetailEvents{

    data class GetMovieDetail(val movieId: Int) : MovieDetailEvents()

    object Refresh: MovieDetailEvents()
}
