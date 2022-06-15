package com.thefork.challenge.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    companion object {
        private const val APP_ID = "62a7bad9bcbcf50cc49cbd46"
        //TODO this should be on local.properties
    }

    @GET("user?limit=10")
    @Headers("app-id: $APP_ID")
    suspend fun getUsers(@Query("page") page: UInt): Response<Page<UserPreview>>

    @GET("user/{id}")
    @Headers("app-id: $APP_ID")
    suspend fun getFullUser(@Path("id") id: String): Response<UserFull>
}
