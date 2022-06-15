package com.mural.common

import android.app.Activity

interface UserScreenRouteContract {
    fun show(dataToPass: String, activity: Activity)
}