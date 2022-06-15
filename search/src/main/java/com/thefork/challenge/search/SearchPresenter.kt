package com.thefork.challenge.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mural.common.module.IoDispatcher
import com.mural.common.module.MainDispatcher
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.UserPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchPresenter @Inject constructor(
    private val api: Api,
    @MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ViewModel(), SearchContract.SearchPresenter {
    //TODO use Lifecycle aware component or a separated real ViewModel?

    private var view: SearchContract.SearchView? = null

    val users: Flow<PagingData<UserPreview>> = Pager(PagingConfig(pageSize = 20)) {
        UsersPagingSource(api)
    }.flow
        .cachedIn(viewModelScope)

    fun getUsersPaged() {
        view?.displayUsersPaged(
            Pager(PagingConfig(pageSize = 20)) {
                UsersPagingSource(api)
            }.flow
                .cachedIn(viewModelScope)
        )
    }

    override fun getUsers() {
        viewModelScope.launch {
            val response = withContext(dispatcherIO) {
                api.userService.getUsers(1u)
            }
            withContext(dispatcherMain) {
                if (response.isSuccessful) {
                    view?.displayUsers(response.body()!!.data)
                } else {
                    view?.displayError()
                }
            }
        }
    }

    override fun attach(view: SearchContract.SearchView) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

}