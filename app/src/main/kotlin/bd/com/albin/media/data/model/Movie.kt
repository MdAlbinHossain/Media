package bd.com.albin.media.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Movie(
    val description: String = "",
    val source: String = "",
    val subtitle: String = "",
    val thumb: String = "",
    val title: String = "",
)
