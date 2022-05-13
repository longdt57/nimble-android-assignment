package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.request.LogoutRequest
import co.nimble.lee.assignment.data.request.RefreshTokenRequest
import co.nimble.lee.assignment.data.request.SignInRequest
import co.nimble.lee.assignment.data.response.EmptyResponse
import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OAuthApiService {

    @POST("oauth/token")
    suspend fun signInWithEmail(@Body request: SignInRequest): ObjectItem<SignInResponse>

    @POST("oauth/token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): ObjectItem<SignInResponse>

    @POST("oauth/revoke")
    suspend fun logout(@Body request: LogoutRequest): ObjectItem<EmptyResponse>
}
