package com.thefork.challenge.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.toDomain
import com.thefork.challenge.domain.User
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val api: Api
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response = api.userService.getUsers(page = nextPage.toUInt())

            val data = response.body()?.data?.map { it.toDomain() } ?: listOf()
            val currentPage = response.body()?.page?.toInt() ?: 0
            val totalPages = response.body()?.total?.toInt() ?: 0

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (currentPage < totalPages && data.isNotEmpty()) {
                    currentPage + 1
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }
}