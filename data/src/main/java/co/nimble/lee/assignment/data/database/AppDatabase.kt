package co.nimble.lee.assignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.nimble.lee.assignment.data.response.SurveyResponse

@Database(
    entities = [SurveyResponse::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
}
