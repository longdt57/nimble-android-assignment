package co.nimblehq.coroutines.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import co.nimble.lee.assignment.data.database.AppDatabase
import co.nimble.lee.assignment.data.database.SurveyDao
import co.nimble.lee.assignment.di.modules.DatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseProvider::class]
)
class TestDatabaseProvider {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).build()
    }

    @Provides
    fun provideSurveyDao(appDatabase: AppDatabase): SurveyDao = appDatabase.surveyDao()
}

const val DATABASE_NAME = "nb-survey"
