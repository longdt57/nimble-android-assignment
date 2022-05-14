package co.nimble.lee.assignment.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import co.nimble.lee.assignment.data.response.SurveyResponse

@Dao
interface SurveyDao {
    @Query("SELECT * FROM SurveyResponse")
    suspend fun getAll(): List<SurveyResponse>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(item: List<SurveyResponse>)

    @Delete
    suspend fun delete(SurveyResponse: SurveyResponse)

    @Query("DELETE FROM SurveyResponse")
    suspend fun clear()
}
