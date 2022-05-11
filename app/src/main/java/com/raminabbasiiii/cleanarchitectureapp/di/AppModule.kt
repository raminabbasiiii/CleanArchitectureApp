package com.raminabbasiiii.cleanarchitectureapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raminabbasiiii.cleanarchitectureapp.data.network.MovieApi
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.movie.MovieDtoMapper
import com.raminabbasiiii.cleanarchitectureapp.data.network.dto.moviedetail.MovieDetailDtoMapper
import com.raminabbasiiii.cleanarchitectureapp.data.repository.MovieRepositoryImpl
import com.raminabbasiiii.cleanarchitectureapp.domain.repository.MovieRepository
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.GetMovieDetailUseCase
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.GetMoviesUseCase
import com.raminabbasiiii.cleanarchitectureapp.domain.util.Constants
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideMovieApiService(retrofitBuilder: Retrofit.Builder): MovieApi {
        return retrofitBuilder
            .build()
            .create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDtoMapper(): MovieDtoMapper {
        return MovieDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMovieDetailDtoMapper(): MovieDetailDtoMapper {
        return MovieDetailDtoMapper()
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: MovieApi,
        movieDtoMapper: MovieDtoMapper,
        movieDetailDtoMapper: MovieDetailDtoMapper
    ) = MovieRepositoryImpl(api, movieDtoMapper, movieDetailDtoMapper) as MovieRepository

    @Singleton
    @Provides
    fun provideGetMoviesUseCase(
        movieRepository: MovieRepository
    ): GetMoviesUseCase{
        return GetMoviesUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun provideSearchMoviesUseCase(
        movieRepository: MovieRepository
    ): SearchMoviesUseCase{
        return SearchMoviesUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun provideGetMovieDetailUseCase(
        movieRepository: MovieRepository
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository)
    }
}



















