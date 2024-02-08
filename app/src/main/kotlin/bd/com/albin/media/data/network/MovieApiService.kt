package bd.com.albin.media.data.network

import bd.com.albin.media.data.model.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("public-test-videos.json")
    suspend fun getMovies(): List<Movie>
}