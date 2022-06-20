package com.thefork.challenge.search.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.thefork.challenge.search.paging.UsersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchPresenterPaged @Inject constructor(
    private val usersPagingSource: UsersPagingSource,
) : ViewModel(), SearchContract.SearchPresenterPaged {

    private var view: SearchContract.SearchViewPaged? = null

    override fun getUsersPaged() {
        view?.displayUsersPaged(
            Pager(PagingConfig(pageSize = 20)) {
                usersPagingSource
            }.flow
                .cachedIn(viewModelScope)
        )
    }

    override fun attach(view: SearchContract.SearchViewPaged) {
        this.view = view
        getUsersPaged()
    }

    override fun detach() {
        view = null
    }

}