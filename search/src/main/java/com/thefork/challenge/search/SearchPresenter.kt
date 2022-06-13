package com.thefork.challenge.search

import com.thefork.challenge.api.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPresenter {

    fun init(view: SearchActivity) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = withContext(Dispatchers.IO) { Api().userService.getUsers(1u) }

            if (response.isSuccessful) {
                view.displayUsers(response.body()!!.data)
            } else {
                view.displayError()
            }
        }
    }

}
