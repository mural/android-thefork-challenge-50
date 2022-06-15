package com.thefork.challenge.user.router

import android.app.Activity
import android.content.Intent
import com.thefork.challenge.routes.UserScreenRouteContract
import com.thefork.challenge.user.UserActivity

class UserScreenRouteImpl : UserScreenRouteContract {

    companion object {
        const val USER_ID = "USER_ID"
    }

    override fun show(userId: String, activity: Activity) {
        activity.startActivity(Intent(activity, UserActivity::class.java).apply {
            putExtra(USER_ID, userId)
        })
    }
}