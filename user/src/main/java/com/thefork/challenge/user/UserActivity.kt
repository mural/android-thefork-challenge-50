package com.thefork.challenge.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.livedata.observeAsState
import com.thefork.challenge.domain.LoadStatus
import com.thefork.challenge.user.router.UserScreenRouteImpl.Companion.USER_ID
import com.thefork.challenge.user.theme.TheForkTheme
import com.thefork.challenge.user.ui.UserScreen
import com.thefork.challenge.user.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollState = rememberScrollState()
            TheForkTheme {
                val navigateUp = this::finish
                val userState = userViewModel.response.observeAsState(LoadStatus.Loading())

                UserScreen(
                    loadStatus = userState.value,
                    scrollState = scrollState,
                    navigateUp = navigateUp
                )
            }
        }
        val userId = intent.getStringExtra(USER_ID)
        userViewModel.init(userId)
    }
}
