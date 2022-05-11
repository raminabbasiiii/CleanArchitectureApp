package com.raminabbasiiii.cleanarchitectureapp.presentation.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.GetMoviesUseCase
import com.raminabbasiiii.cleanarchitectureapp.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject
constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    ): ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        onEvent(MoviesEvents.GetMovies)
    }

    fun onEvent(event: MoviesEvents) {
        when(event) {
            is MoviesEvents.GetMovies -> {
                getMovies()
            }
            is MoviesEvents.SearchMovies -> {
                searchMovies()
            }
            is MoviesEvents.UpdateQuery -> {
                updateQuery(event.query)
            }
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            delay(1000)
            getMoviesUseCase()
                .cachedIn(viewModelScope)
                .collect { movies ->
                    _uiState.update { it.copy(movies = movies) }
                }
        }
    }

    private fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun searchMovies() {
        viewModelScope.launch {
            searchMoviesUseCase(uiState.value.query)
                .cachedIn(viewModelScope)
                .collect { movies ->
                    _uiState.update { it.copy(movies = movies) }
                }
        }
    }

}