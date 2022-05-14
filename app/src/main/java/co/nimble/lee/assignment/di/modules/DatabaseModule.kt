package co.nimble.lee.assignment.di.modules

import android.content.Context
import androidx.room.Room
import co.nimble.lee.assignment.data.database.AppDatabase
import co.nimble.lee.assignment.data.database.SurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseProvider {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideSurveyDao(appDatabase: AppDatabase): SurveyDao = appDatabase.surveyDao()
}

const val DATABASE_NAME = "nb-survey"
