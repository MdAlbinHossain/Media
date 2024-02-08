package bd.com.albin.media.common

import java.util.Base64

fun String.encodeUrl(): String {
    return Base64.getUrlEncoder().encodeToString(this.toByteArray())
}

fun String.decodeUrl(): String {
    return String(Base64.getUrlDecoder().decode(this))
}
