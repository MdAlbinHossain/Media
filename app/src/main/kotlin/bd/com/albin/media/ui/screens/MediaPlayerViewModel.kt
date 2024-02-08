package bd.com.albin.media.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import bd.com.albin.media.common.decodeUrl
import bd.com.albin.media.ui.MediaAppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, val player: Player
) : ViewModel() {

     private val mediaUrl: String =
        checkNotNull(savedStateHandle[MediaAppDestination.MediaPlayer.URL_ARG])

    init {
        player.playWhenReady = true
        player.prepare()
        player.setMediaItem(MediaItem.fromUri(mediaUrl.decodeUrl()))
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}