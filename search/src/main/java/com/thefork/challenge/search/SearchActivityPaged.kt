package com.thefork.challenge.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.thefork.challenge.domain.User
import com.thefork.challenge.routes.UserScreenRouteContract
import com.thefork.challenge.search.adapters.OnItemClickListenerPaged
import com.thefork.challenge.search.adapters.UserLoadStateAdapter
import com.thefork.challenge.search.adapters.UsersAdapterPaged
import com.thefork.challenge.search.databinding.ActivitySearchBinding
import com.thefork.challenge.search.presenters.SearchContract
import com.thefork.challenge.search.presenters.SearchPresenterPaged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivityPaged : AppCompatActivity(), OnItemClickListenerPaged,
    SearchContract.SearchViewPaged {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var usersAdapter: UsersAdapterPaged
    private val searchPresenterPaged: SearchPresenterPaged by viewModels()

    @Inject
    lateinit var userScreenRouteContract: UserScreenRouteContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usersAdapter = UsersAdapterPaged(this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = usersAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter { usersAdapter.retry() }
            )
        }

        searchPresenterPaged.attach(this)
    }

    override fun displayUsersPaged(users: Flow<PagingData<User>>) {
        lifecycleScope.launch {
            users.collectLatest {
                usersAdapter.submitData(it)
            }
        }
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
        searchPresenterPaged.detach()
        super.onDestroy()
    }
}
