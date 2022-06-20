package com.thefork.challenge.user.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thefork.challenge.api.Api
import com.thefork.challenge.api.toDomain
import com.thefork.challenge.domain.LoadStatus
import com.thefork.challenge.domain.User
import com.thefork.challenge.module.IoDispatcher
import com.thefork.challenge.module.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val api: Api,
    @MainDispatcher private val dispatcherMain: CoroutineDispatcher,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _response: MutableLiveData<LoadStatus<User>> = MutableLiveData()
    val response: LiveData<LoadStatus<User>> = _response

    fun init(userId: String) {
        getFullUser(userId)
    }

    private fun getFullUser(userId: String) {
        viewModelScope.launch {
            withContext(dispatcherMain) {
                _response.value =
                    withContext(dispatcherIO) {
                        try {
                            LoadStatus.Success(api.userService.getFullUser(id = userId).body()?.toDomain())
                        } catch (e: Exception) {
                            LoadStatus.Error("Error getting users")
                        }
                    }
            }
        }
    }

}