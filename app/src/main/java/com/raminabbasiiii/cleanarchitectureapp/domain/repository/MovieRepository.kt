package com.raminabbasiiii.cleanarchitectureapp.domain.repository

import androidx.paging.PagingData
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovies(): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
}