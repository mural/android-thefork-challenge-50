package com.thefork.challenge.search.presenters

import com.thefork.challenge.api.Api
import com.thefork.challenge.api.Page
import com.thefork.challenge.api.UserPreview
import com.thefork.challenge.search.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


class SearchPresenterTest {

    private var api = mockk<Api>()
    private lateinit var view: SearchContract.SearchView
    private lateinit var presenter: SearchPresenter

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        view = mockk()
        presenter =
            SearchPresenter(
                api = api,
                dispatcherMain = coroutinesTestRule.testDispatcher,
                dispatcherIO = coroutinesTestRule.testDispatcher
            )
        presenter.attach(view)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get users successfully and display those`() {
        coroutinesTestRule.testDispatcher.run() {

            val usersResponse: Response<Page<UserPreview>>

            val user1 = UserPreview(
                id = "id.1",
                title = "mrs",
                firstName = "First",
                lastName = "User",
                picture = "some Url"
            )
            val users = listOf(user1)
            usersResponse = Response.success(Page(data = users, page = 1u, total = 1u))

            coEvery { api.userService.getUsers(any()) } returns usersResponse

            presenter.getUsers()

            coVerify(exactly = 1) { api.userService.getUsers(any()) }
            coVerify(exactly = 1) { view.displayUsers(any()) }
            coVerify(exactly = 0) { view.displayError() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `error getting users then display that error`() {
        coroutinesTestRule.testDispatcher.run() {

            val usersResponse: Response<Page<UserPreview>> =
                Response.error(400, ResponseBody.create(MediaType.parse(""), ""))
            coEvery { api.userService.getUsers(any()) } returns usersResponse

            presenter.getUsers()

            coVerify(exactly = 1) { api.userService.getUsers(any()) }
            coVerify(exactly = 0) { view.displayUsers(any()) }
            coVerify(exactly = 1) { view.displayError() }
        }
    }

    @After
    fun tearDown() {
        presenter.detach()
    }

}
