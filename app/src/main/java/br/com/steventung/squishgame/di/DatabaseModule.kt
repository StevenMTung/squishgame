package br.com.steventung.squishgame.di

import android.content.Context
import androidx.room.Room
import br.com.steventung.squishgame.data.dao.GameDao
import br.com.steventung.squishgame.data.database.SquishGameDatabase
import br.com.steventung.squishgame.data.repository.GameRepository
import br.com.steventung.squishgame.data.repository.GameRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


const val SQUISH_DATABASE = "squishGame.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SquishGameDatabase {
        return Room.databaseBuilder(
            context,
            SquishGameDatabase::class.java,
            SQUISH_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideGameDao(database: SquishGameDatabase): GameDao {
        return database.gameDao()
    }

    @Provides
    @Singleton
    fun provideGameRepository(gameDao: GameDao): GameRepository {
        return GameRepositoryImp(gameDao)
    }
}