package bd.com.albin.media.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bd.com.albin.media.common.encodeUrl
import bd.com.albin.media.ui.screens.HomeScreen
import bd.com.albin.media.ui.screens.HomeViewModel
import bd.com.albin.media.ui.screens.MediaPlayerScreen
import bd.com.albin.media.ui.screens.MediaPlayerViewModel

sealed class MediaAppDestination(val route: String) {
    data object Home : MediaAppDestination(route = "home")
    data object MediaPlayer : MediaAppDestination(route = "media") {
        const val URL_ARG = "url"
        val routeWithArg = "$route/{$URL_ARG}"
    }
}

@Composable
fun MediaApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MediaAppDestination.Home.route,
        modifier = modifier,
    ) {

        composable(route = MediaAppDestination.Home.route) {
            val viewmodel: HomeViewModel = hiltViewModel()
            HomeScreen(onMovieClick = { route ->
                navController.navigate("${MediaAppDestination.MediaPlayer.route}/${route.encodeUrl()}")
            }, uiState = viewmodel.homeUiState, retryAction = viewmodel::getMovies)
        }

        composable(
            MediaAppDestination.MediaPlayer.routeWithArg,
            arguments = listOf(navArgument(MediaAppDestination.MediaPlayer.URL_ARG) {
                type = NavType.StringType
            }),
        ) {
            val viewModel: MediaPlayerViewModel = hiltViewModel()
            MediaPlayerScreen(modifier = Modifier, viewModel.player)
        }
    }
}