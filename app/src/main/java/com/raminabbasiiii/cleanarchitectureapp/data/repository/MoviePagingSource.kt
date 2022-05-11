package com.raminabbasiiii.cleanarchitectureapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raminabbasiiii.cleanarchitectureapp.data.network.MovieApi
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.movie.MovieDtoMapper
import com.raminabbasiiii.cleanarchitectureapp.domain.model.Movie
import com.raminabbasiiii.cleanarchitectureapp.domain.util.Constants.Companion.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class MoviePagingSource
@Inject
constructor(
    private val api: MovieApi,
    private val movieDtoMapper: MovieDtoMapper,
    private val query: String? = null
    ): PagingSource<Int,Movie>(){

    override fun getRefreshKey(state: PagingState<Int,Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX

            val response = if (query == null)
                api.getMovies(page)
            else
                api.searchMovies(query,page)

            val movies = movieDtoMapper.toDomainList(response.movies)

            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.metaData.currentPage.toInt() == response.metaData.pageCount) null else page + 1
            )
        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}