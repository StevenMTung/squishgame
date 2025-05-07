package br.com.steventung.squishgame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.steventung.squishgame.domain.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert
    suspend fun createGame(game: Game)

    @Query("DELETE FROM GAME WHERE id = :id ")
    suspend fun removeGame(id: String)

    @Query("SELECT * FROM Game ORDER BY scoreTime ASC")
    fun getGameListAscendingByScoreTime(): Flow<List<Game?>>

    @Query("SELECT * FROM Game")
    fun getGameList(): Flow<List<Game?>>

    @Query("SELECT * FROM Game ORDER BY scoreTime ASC LIMIT 1")
    fun getFastestPlayer(): Flow<Game?>
}