package com.thefork.challenge.user.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.UserFull
import com.thefork.challenge.module.IoDispatcher
import com.thefork.challenge.module.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val api: Api,
    @MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _response: MutableLiveData<Response<UserFull>> = MutableLiveData()
    val response: LiveData<Response<UserFull>> = _response

    fun getFullUser(userId: String) {
        viewModelScope.launch {
            withContext(dispatcherMain) {
                _response.value =
                    withContext(dispatcherIO) {
                        try {
                            api.userService.getFullUser(id = userId)
                        } catch (e: Exception) {
                            Response.error(400, ResponseBody.create(MediaType.parse(""), ""))
                        }
                    }
            }
        }
    }

}