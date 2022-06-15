package com.thefork.challenge.search.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thefork.challenge.api.Api
import com.thefork.challenge.module.IoDispatcher
import com.thefork.challenge.module.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchPresenter @Inject constructor(
    private val api: Api,
    @MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ViewModel(), SearchContract.SearchPresenter {
    // TODO use Lifecycle aware component or a separated real ViewModel?

    private var view: SearchContract.SearchView? = null

    override fun getUsers() {
        viewModelScope.launch {
            val response = withContext(dispatcherIO) {
                api.userService.getUsers(1u)
            }
            withContext(dispatcherMain) {
                if (response.isSuccessful) {
                    view?.displayUsers(response.body()?.data ?: listOf())
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