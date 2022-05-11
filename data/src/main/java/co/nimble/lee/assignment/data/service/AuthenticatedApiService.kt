package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.response.EmptyResponse
import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.ObjectList
import co.nimble.lee.assignment.data.response.SurveyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthenticatedApiService {

    @POST("oauth/revoke")
    suspend fun logout(@Body request: LogoutRequest): ObjectItem<EmptyResponse>

    @GET("surveys")
    suspend fun getSurveyList(
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") pageSize: Int
    ): ObjectList<SurveyResponse>
}
