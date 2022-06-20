package com.thefork.challenge.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.thefork.challenge.domain.User
import com.thefork.challenge.search.paging.UsersPagingSource
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val usersPagingSource: UsersPagingSource,
) {
    fun getUsers(): Pager<Int, User> {
        return Pager(PagingConfig(pageSize = 20)) {
            usersPagingSource
        }
    }
}