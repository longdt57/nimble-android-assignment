package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.response.SurveyMetaResponse
import co.nimble.lee.assignment.data.response.base.ObjectItem
import co.nimble.lee.assignment.data.response.SurveyResponse
import co.nimble.lee.assignment.data.response.UserResponse
import co.nimble.lee.assignment.data.response.base.MetaObjectList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("surveys")
    suspend fun getSurveys(
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") pageSize: Int
    ): MetaObjectList<SurveyResponse, SurveyMetaResponse>

    @GET("me")
    suspend fun getUser(): ObjectItem<UserResponse>
}
