package br.com.steventung.squishgame.data.repository

import br.com.steventung.squishgame.data.dao.GameDao
import br.com.steventung.squishgame.domain.model.Game
import kotlinx.coroutines.flow.Flow

class GameRepositoryImp(
    private val gameDao: GameDao
): GameRepository {
    override suspend fun createGame(game: Game) {
        gameDao.createGame(game)
    }

    override suspend fun removeGame(id: String) {
        gameDao.removeGame(id)
    }

    override fun getGameListAscendingByScoreTime(): Flow<List<Game?>> {
        return gameDao.getGameListAscendingByScoreTime()
    }

    override fun getGameList(): Flow<List<Game?>> {
        return gameDao.getGameList()
    }

    override fun getFastestPlayer(): Flow<Game?> {
        return gameDao.getFastestPlayer()
    }
}