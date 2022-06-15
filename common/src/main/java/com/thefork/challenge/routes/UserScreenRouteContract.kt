package com.thefork.challenge.routes

import android.app.Activity

interface UserScreenRouteContract {
    fun show(dataToPass: String, activity: Activity)
    //TODO here can be a navigation graph for example instead of an Activity
}