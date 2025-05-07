package br.com.steventung.squishgame.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.steventung.squishgame.data.dao.GameDao
import br.com.steventung.squishgame.domain.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = true)
abstract class SquishGameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}