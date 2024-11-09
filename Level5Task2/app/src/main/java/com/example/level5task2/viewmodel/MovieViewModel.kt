package com.example.level5task2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level5task2.data.model.Movie
import com.example.level5task2.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _favorites = MutableLiveData<List<Movie>>()
    val favorites: LiveData<List<Movie>> get() = _favorites

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchFavorites()
    }

    private fun fetchFavorites() {
        viewModelScope.launch {
            val favoriteMovies = FirestoreHelper.getFavorites()
            _favorites.value = favoriteMovies
            _movies.value = favoriteMovies
        }
    }

    fun searchMovies(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.searchMovies(query)
            Log.d("MovieViewModel", "Search result: $result")


            val favoriteIds = _movies.value?.filter { it.isFavorite }?.map { it.id } ?: emptyList()


            _movies.value = result?.map { movie ->
                movie.copy(isFavorite = favoriteIds.contains(movie.id))
            } ?: emptyList()

            _isLoading.value = false
        }
    }

    fun favoriteMovie(movie: Movie) {
        viewModelScope.launch {
            val updatedMovies = _movies.value?.map {
                if (it.id == movie.id) {
                    val updatedMovie = it.copy(isFavorite = !it.isFavorite)
                    if (updatedMovie.isFavorite) {
                        FirestoreHelper.addFavorite(updatedMovie)
                    } else {
                        FirestoreHelper.removeFavorite(updatedMovie)
                    }
                    updatedMovie
                } else {
                    it
                }
            }
            _movies.value = updatedMovies ?: emptyList()
            fetchFavorites()
        }
    }

    fun showFavoritesOnly() {
        _movies.value = _favorites.value ?: emptyList()
    }
}