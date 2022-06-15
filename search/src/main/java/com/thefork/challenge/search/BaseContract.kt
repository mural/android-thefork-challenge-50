package com.thefork.challenge.search

import androidx.annotation.UiThread

interface BaseContract {

    @UiThread
    interface View

    interface Presenter<V : View> {

        fun attach(view: V)

        fun detach()
    }

}