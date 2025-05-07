package br.com.steventung.squishgame.utils

import java.util.Locale

fun Long.toTimeString(): String {
    val minutes = (this / 1000) / 60
    val seconds = (this / 1000) % 60
    val millis = this % 1000
    return String.format(
        Locale.ROOT,
        "%02d:%02d:%03d",
        minutes,
        seconds,
        millis
    )
}