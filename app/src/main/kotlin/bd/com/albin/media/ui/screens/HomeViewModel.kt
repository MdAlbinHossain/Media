package bd.com.albin.media.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.com.albin.media.data.model.Movie
import bd.com.albin.media.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMovies()
    }

    fun getMovies() = viewModelScope.launch {
        homeUiState = HomeUiState.Loading
        homeUiState = try {
            HomeUiState.Success(movieRepository.getMovies())
        } catch (e: IOException) {
            HomeUiState.Error(e.localizedMessage ?: "Something is wrong")
        } catch (e: HttpException) {
            HomeUiState.Error(e.localizedMessage ?: "Something is wrong")
        }
    }
}

sealed class HomeUiState() {
    data object Loading : HomeUiState()
    data class Success(val movies: List<Movie>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}