package bd.com.albin.media.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import bd.com.albin.media.common.decodeUrl
import bd.com.albin.media.ui.MediaAppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, val player: Player
) : ViewModel() {

    var playBackState by mutableIntStateOf(ExoPlayer.STATE_IDLE)
        private set

    private val playbackStateListener: Player.Listener = playbackStateListener()

    private val mediaUrl: String =
        checkNotNull(savedStateHandle[MediaAppDestination.MediaPlayer.URL_ARG])

    init {
        player.setMediaItem(MediaItem.fromUri(mediaUrl.decodeUrl()))
        player.playWhenReady = true
        player.addListener(playbackStateListener)
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.removeListener(playbackStateListener)
        player.release()
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            playBackState = playbackState
        }
    }
}