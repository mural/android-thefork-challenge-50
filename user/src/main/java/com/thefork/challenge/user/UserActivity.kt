package com.thefork.challenge.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.thefork.challenge.user.UserScreenRouteImpl.Companion.USER_ID
import com.thefork.challenge.user.theme.TheForkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra(USER_ID)
        setContent {
            TheForkTheme {
                val navigateUp = this::finish
                UserScreen(userId = userId, navigateUp = navigateUp)
            }
        }
    }
}
