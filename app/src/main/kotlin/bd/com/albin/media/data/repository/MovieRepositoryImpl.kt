package bd.com.albin.media.data.repository

import bd.com.albin.media.data.model.Movie
import bd.com.albin.media.data.network.MovieApiService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApiService: MovieApiService) :
    MovieRepository {
    override suspend fun getMovies(): List<Movie> = movieApiService.getMovies()
}