package bd.com.albin.media.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import bd.com.albin.media.ui.theme.MediaTheme


@OptIn(UnstableApi::class)
@Composable
fun MediaPlayerScreen(
    modifier: Modifier = Modifier,
    playbackState: Int = 0,
    player: Player = ExoPlayer.Builder(LocalContext.current).build()
) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(factory = { context ->
            PlayerView(context).also {
                it.player = player
            }
        }, update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }

                else -> Unit
            }
        }, modifier = Modifier.fillMaxSize()
        )
        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            LinearProgressIndicator()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewMediaScreen() {
    MediaTheme {
        MediaPlayerScreen()
    }
}