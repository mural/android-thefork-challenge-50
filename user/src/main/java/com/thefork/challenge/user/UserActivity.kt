package com.thefork.challenge.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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

        val userId = intent.getStringExtra(USER_ID)
        userViewModel.init(userId!!) //TODO ver estados!

        setContent {
            TheForkTheme {
                val navigateUp = this::finish
                UserScreen(
                    userViewModel.response.observeAsState(LoadStatus.Loading()),
                    navigateUp = navigateUp
                )
            }
        }
    }
}
