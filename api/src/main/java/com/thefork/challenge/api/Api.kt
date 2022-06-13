package com.thefork.challenge.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    val userService: UserService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyapi.io/data/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
    }

}
