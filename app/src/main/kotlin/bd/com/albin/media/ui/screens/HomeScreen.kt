package bd.com.albin.media.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.com.albin.media.R
import bd.com.albin.media.data.model.Movie
import bd.com.albin.media.ui.theme.MediaTheme
import coil.compose.SubcomposeAsyncImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (String) -> Unit = {},
    uiState: HomeUiState = HomeUiState.Success(listOf()),
    retryAction: () -> Unit = {},
) {
    when (uiState) {
        HomeUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HomeUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
                Button(onClick = retryAction) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }

        is HomeUiState.Success -> {
            HomeBody(modifier = modifier, movies = uiState.movies, onMovieClick = onMovieClick)
        }
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier, movies: List<Movie> = emptyList(), onMovieClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            MovieRow("Trending Now", movies = movies, onMovieClick = onMovieClick)
        }
        item {
            MovieRow("Most Popular", movies = movies.reversed(), onMovieClick = onMovieClick)
        }
        item {
            MovieRow("Top Rated", movies = movies, onMovieClick = onMovieClick)
        }
        item {
            MovieRow("My List", movies = movies.reversed(), onMovieClick = onMovieClick)
        }
    }
}

@Composable
fun MovieRow(header: String = "Row Header", movies: List<Movie>, onMovieClick: (String) -> Unit) {
    Column {
        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                Spacer(modifier = Modifier.size(0.dp))
            }
            items(items = movies) { movie ->
                MovieItem(movie = movie, onMovieClick = onMovieClick)
            }
            item {
                Spacer(modifier = Modifier.size(0.dp))
            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie, onMovieClick: (String) -> Unit) {
    SubcomposeAsyncImage(
        model = movie.thumb,
        contentDescription = movie.title,
        modifier = Modifier
            .size(height = 240.dp, width = 135.dp)
            .clip(RectangleShape)
            .clickable(onClick = { onMovieClick(movie.source) }),
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        contentScale = ContentScale.Crop
    )
}


@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    MediaTheme {
        HomeScreen(uiState = HomeUiState.Success(movies = listOf(Movie(thumb = "https://albin.com.bd/assets/favicon.svg"))))
    }
}