package com.thefork.challenge.user.repository

import com.thefork.challenge.api.Api
import com.thefork.challenge.api.toDomain
import com.thefork.challenge.domain.User
import com.thefork.challenge.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    suspend fun getUser(userId: String): User? {
        return withContext(dispatcherIO) {
            api.userService.getFullUser(id = userId).body()?.toDomain()
        }
    }
}