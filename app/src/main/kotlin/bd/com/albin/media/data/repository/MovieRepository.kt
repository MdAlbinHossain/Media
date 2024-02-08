package bd.com.albin.media.data.repository

import bd.com.albin.media.data.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}