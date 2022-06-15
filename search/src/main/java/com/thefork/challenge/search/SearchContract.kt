package com.thefork.challenge.search

import androidx.paging.PagingData
import com.thefork.challenge.api.UserPreview
import kotlinx.coroutines.flow.Flow

interface SearchContract : BaseContract {

    interface SearchView : BaseContract.View {

        fun displayUsers(users: List<UserPreview>)

        fun displayUsersPaged(users: Flow<PagingData<UserPreview>>)

        fun displayError()
    }

    interface SearchPresenter : BaseContract.Presenter<SearchView> {

        fun getUsers()
    }

}