package com.raminabbasiiii.cleanarchitectureapp.domain.usecase

import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail
import com.raminabbasiiii.cleanarchitectureapp.domain.repository.MovieRepository
import com.raminabbasiiii.cleanarchitectureapp.domain.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetMovieDetailUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository
    ) {

    operator fun invoke(movieId: Int): Flow<DataState<MovieDetail>>
    = flow {
        try {
            emit(DataState.Loading())
            val movieDetail = movieRepository.getMovieDetail(movieId)
            emit(DataState.Success(movieDetail))
        } catch (e: Exception) {
            emit(DataState.Error("Check your internet connection!"))
        }
    }
}