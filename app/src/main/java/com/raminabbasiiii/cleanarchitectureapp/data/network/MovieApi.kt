package com.raminabbasiiii.cleanarchitectureapp.data.network

import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.moviedetail.MovieDetailDto
import com.raminabbasiiii.cleanarchitectureapp.data.network.response.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movies")
    suspend fun getMovies(
        @Query("page") page: Int? = null
    ): MovieListResponse

    @GET("movies")
    suspend fun searchMovies(
        @Query("q") name: String,
        @Query("page") page: Int? = null
    ): MovieListResponse

    @GET("movies/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetailDto
}