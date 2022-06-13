package com.thefork.challenge.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thefork.challenge.api.UserPreview

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        SearchPresenter().init(this)
    }

    fun displayUsers(users: List<UserPreview>) {
        findViewById<RecyclerView>(R.id.recycler_view).adapter = UsersAdapter(users)
    }

    fun displayError() {
        Snackbar
            .make(findViewById(R.id.recycler_view), R.string.error, Snackbar.LENGTH_LONG)
            .show()
    }

    fun navigateToUser(user: UserPreview) {
        startActivity(Intent().apply {
            setClassName("com.thefork.challenge", "com.thefork.challenge.user.UserActivity")
            putExtra("USER_ID", user.id)
        })
    }
}
