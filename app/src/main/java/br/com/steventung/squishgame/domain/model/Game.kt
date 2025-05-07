package br.com.steventung.squishgame.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Game(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val gameUserName: String,
    val scoreTime: Long
)
