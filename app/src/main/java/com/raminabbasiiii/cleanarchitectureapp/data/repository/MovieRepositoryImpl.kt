package com.raminabbasiiii.cleanarchitectureapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.raminabbasiiii.cleanarchitectureapp.data.network.MovieApi
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.movie.MovieDtoMapper
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.moviedetail.MovieDetailDtoMapper
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.domain.model.MovieDetail
import com.raminabbasiiii.cleanarchitectureapp.domain.repository.MovieRepository
import com.raminabbasiiii.cleanarchitectureapp.domain.util.Constants.Companion.PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl
@Inject
constructor(
    private val api: MovieApi,
    private val movieDtoMapper: MovieDtoMapper,
    private val movieDetailDtoMapper: MovieDetailDtoMapper
    ): MovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(PAGINATION_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api,movieDtoMapper) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(PAGINATION_PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(api,movieDtoMapper,query) }
        ).flow
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        return movieDetailDtoMapper
            .mapToDomainModel(
                api.getMovieDetail(movieId)
            )
    }
}