package com.raminabbasiiii.cleanarchitectureapp.domain.usecase

import androidx.paging.PagingData
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.domain.repository.MovieRepository
import com.raminabbasiiii.cleanarchitectureapp.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetMoviesUseCase
@Inject
constructor(
    private val movieRepository: MovieRepository
    ) {

    operator fun invoke(): Flow<PagingData<Movie>> = movieRepository.getMovies()


/*flow {
        try {
            emit(DataState.Loading())
            val movies = movieRepository.getMovies(page)
            emit(DataState.Success(movies))
        } catch (e: Exception) {
            emit(DataState.Error("Check your internet connection!"))
        }
    }*/
}