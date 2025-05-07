package br.com.steventung.squishgame.data.repository

import br.com.steventung.squishgame.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun createGame(game: Game)

    suspend fun removeGame(id: String)

    fun getGameListAscendingByScoreTime(): Flow<List<Game?>>

    fun getGameList(): Flow<List<Game?>>

    fun getFastestPlayer(): Flow<Game?>
}