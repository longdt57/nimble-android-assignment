package co.nimble.lee.assignment.data.service

import co.nimble.lee.assignment.data.response.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}
