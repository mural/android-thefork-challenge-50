package com.thefork.challenge.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Api @Inject constructor() {

    companion object {
        const val BASE_URL = "https://dummyapi.io/data/v1/"
    }

    private var retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        set(value) {
            field = value
            userService = createUserService()
        }

    var userService: UserService = createUserService()

    private fun createUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }

}
