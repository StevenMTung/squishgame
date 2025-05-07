package br.com.steventung.squishgame.utils

import android.content.Context
import android.media.MediaPlayer


class SoundManager(private val context: Context) {

    private val sounds = mutableMapOf<Int, MediaPlayer>()

    fun loadSound(listResId: List<Int>) {
        listResId.forEach { resId ->
            if (!sounds.containsKey(resId)) {
                val mediaPlayer = MediaPlayer.create(context, resId)
                sounds[resId] = mediaPlayer
            }
        }
    }

    fun playSound(resId: Int) {
        val mediaPlayer = sounds[resId]
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.seekTo(0)
            }
            it.start()
        }
    }

    fun release() {
        sounds.values.forEach { it.release() }
        sounds.clear()
    }

    fun getCurrentPlaybackMilliSeconds(resId: Int): Long {
        return sounds[resId]?.duration?.toLong() ?: 0L
    }
}