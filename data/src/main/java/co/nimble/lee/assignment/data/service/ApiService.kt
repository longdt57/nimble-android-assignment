package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.ObjectList
import co.nimble.lee.assignment.data.response.SurveyResponse
import co.nimble.lee.assignment.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("surveys")
    suspend fun getSurveys(
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") pageSize: Int
    ): ObjectList<SurveyResponse>

    @GET("me")
    suspend fun getUser(): ObjectItem<UserResponse>
}
