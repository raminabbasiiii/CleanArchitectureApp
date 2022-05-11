package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.GetMovieDetailUseCase
import com.raminabbasiiii.cleanarchitectureapp.domain.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel
@Inject
constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private var job: Job? = null

    init {
        savedStateHandle.get<Int>("movie_id")?.let { movieId ->
            onEvent(MovieDetailEvents.GetMovieDetail(movieId))
        }
    }

    fun onEvent(event: MovieDetailEvents) {
        when(event) {
            is MovieDetailEvents.GetMovieDetail -> {
                getMovieDetails(event.movieId)
            }
            is MovieDetailEvents.Refresh -> {
                refresh()
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        job?.cancel()
        job = getMovieDetailUseCase(movieId)
            .onEach {
                    dataState ->
                when(dataState) {
                    is DataState.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is DataState.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiState.update { it.copy(errorMessage = null) }
                        dataState.data?.let { movieDetail ->
                            _uiState.update { it.copy(movieDetail = movieDetail) }
                        }
                    }
                    is DataState.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _uiState.update { it.copy(errorMessage = dataState.message) }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun refresh() {
        savedStateHandle.get<Int>("movie_id")?.let { movieId ->
            getMovieDetails(movieId)
        }
    }
}




















