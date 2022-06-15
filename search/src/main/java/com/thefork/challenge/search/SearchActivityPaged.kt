package com.thefork.challenge.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.mural.common.UserScreenRouteContract
import com.thefork.challenge.api.UserPreview
import com.thefork.challenge.search.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchActivityPaged : AppCompatActivity(), OnItemClickListenerPaged,
    SearchContract.SearchView {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var usersAdapter: UsersAdapterPaged
    private val searchPresenter: SearchPresenter by viewModels()

    @Inject
    lateinit var userScreenRouteContract: UserScreenRouteContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        searchPresenter.attach(this)
        //TODO use live data?

        usersAdapter = UsersAdapterPaged(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = usersAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter { usersAdapter.retry() }
            )
        }

        searchPresenter.getUsersPaged()
    }

    override fun displayUsers(users: List<UserPreview>) {
    }

    override fun displayUsersPaged(users: Flow<PagingData<UserPreview>>) {
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

    private fun navigateToUser(user: UserPreview) {
        userScreenRouteContract.show(user.id, this)
    }

    override fun onItemClicked(user: UserPreview) {
        navigateToUser(user)
    }

    override fun onDestroy() {
        searchPresenter.detach()
        super.onDestroy()
    }
}
