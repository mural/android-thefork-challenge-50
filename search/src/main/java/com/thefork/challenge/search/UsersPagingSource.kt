package com.thefork.challenge.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.UserPreview
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val api: Api
) : PagingSource<Int, UserPreview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserPreview> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val response = api.userService.getUsers(page = nextPage.toUInt())

            LoadResult.Page(
                data = response.body()?.data!!, //TODO check also for NextKey !
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.body()?.page!!.toInt() < response.body()?.total!!.toInt() && response.body()?.data!!.isNotEmpty()) { response.body()?.page!!.toInt() + 1 } else { null }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserPreview>): Int? {
        return null
    }
}