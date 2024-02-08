package bd.com.albin.media.ui.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import bd.com.albin.media.R


@OptIn(UnstableApi::class)
@Composable
fun MediaPlayerScreen(modifier: Modifier = Modifier) {
    val mediaItem = MediaItem.fromUri(stringResource(R.string.bigbuckbunny_mp4))

    val localContext = LocalContext.current
    val player = ExoPlayer.Builder(localContext).build()
    val playWhenReady by rememberSaveable { mutableStateOf(true) }
    player.setMediaItem(mediaItem)
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady
    }
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


    AndroidView(factory = { context ->
        PlayerView(context).also {
            it.player = ExoPlayer.Builder(context).build().also { media ->
                media.setMediaItem(mediaItem)
                media.prepare()
                media.playWhenReady = true
            }
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
    }, modifier = modifier
        .fillMaxWidth()
        .aspectRatio(16 / 9f)
    )
}