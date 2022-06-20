package com.thefork.challenge.domain

sealed class LoadStatus<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : LoadStatus<T>() {
        override fun equals(other: Any?): Boolean {
            return other is Loading<*>
        }
    }

    class Success<T>(data: T?) : LoadStatus<T>(data)
    class Error<T>(message: String?, data: T? = null) : LoadStatus<T>(data, message)

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LoadStatus<*> -> {
                this.data == other.data
            }
            else -> false
        }
    }
}