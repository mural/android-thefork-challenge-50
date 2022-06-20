package com.thefork.challenge.user

import androidx.lifecycle.Observer
import org.junit.Assert

class TestableObserver<T> : Observer<T> {
    private val history: MutableList<T> = mutableListOf()

    override fun onChanged(value: T) {
        history.add(value)
    }

    fun assertAllEmitted(values: List<T>) {
        Assert.assertEquals(values.count(), history.count())

        history.forEachIndexed { index, t ->
            Assert.assertEquals(values[index], t)
        }
    }
}