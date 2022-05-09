package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.request.SignInRequest
import co.nimble.lee.assignment.data.response.ObjectItem
import co.nimble.lee.assignment.data.response.SignInResponse
import co.nimble.lee.assignment.data.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>

    @POST("oauth/token")
    suspend fun signInWithEmail(@Body request: SignInRequest): ObjectItem<SignInResponse>
}
