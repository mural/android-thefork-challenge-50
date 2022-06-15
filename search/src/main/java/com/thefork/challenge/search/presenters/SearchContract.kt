package com.thefork.challenge.search.presenters

import androidx.paging.PagingData
import com.thefork.challenge.api.UserPreview
import com.thefork.challenge.search.BaseContract
import kotlinx.coroutines.flow.Flow

interface SearchContract : BaseContract {

    interface SearchViewBase : BaseContract.View {

        fun displayError()
    }

    interface SearchView : SearchViewBase {

        fun displayUsers(users: List<UserPreview>)
    }

    interface SearchViewPaged : SearchViewBase {

        fun displayUsersPaged(users: Flow<PagingData<UserPreview>>)
    }

    interface SearchPresenter : BaseContract.Presenter<SearchView> {

        fun getUsers()
    }

    interface SearchPresenterPaged : BaseContract.Presenter<SearchViewPaged> {

        fun getUsersPaged()
    }
}