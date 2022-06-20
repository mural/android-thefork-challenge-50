package com.thefork.challenge.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.thefork.challenge.domain.User
import com.thefork.challenge.routes.UserScreenRouteContract
import com.thefork.challenge.search.adapters.OnItemClickListener
import com.thefork.challenge.search.adapters.UsersAdapter
import com.thefork.challenge.search.databinding.ActivitySearchBinding
import com.thefork.challenge.search.presenters.SearchContract
import com.thefork.challenge.search.presenters.SearchPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnItemClickListener, SearchContract.SearchView {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var usersAdapter: UsersAdapter
    private val searchPresenter: SearchPresenter by viewModels()

    @Inject
    lateinit var userScreenRouteContract: UserScreenRouteContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        searchPresenter.attach(this)
    }

    override fun displayUsers(users: List<User>) {
        usersAdapter = UsersAdapter(users, this)
        binding.recyclerView.adapter = usersAdapter
    }

    override fun displayError() {
        Snackbar
            .make(binding.recyclerView, R.string.error, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun navigateToUser(user: User) {
        userScreenRouteContract.show(user.id, this)
    }

    override fun onItemClicked(user: User) {
        navigateToUser(user)
    }

    override fun onDestroy() {
        searchPresenter.detach()
        super.onDestroy()
    }

}
