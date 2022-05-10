package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.response.EmptyResponse
import co.nimble.lee.assignment.data.response.ObjectItem
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticatedApiService {

    @POST("oauth/revoke")
    fun logout(@Body request: LogoutRequest): ObjectItem<EmptyResponse>
}
