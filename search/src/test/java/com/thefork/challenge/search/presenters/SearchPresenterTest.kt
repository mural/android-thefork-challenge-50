package com.thefork.challenge.search.presenters

import com.thefork.challenge.api.Api
import com.thefork.challenge.api.Page
import com.thefork.challenge.api.UserPreview
import com.thefork.challenge.api.toDomain
import com.thefork.challenge.domain.User
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

            val usersApiResponse: Response<Page<UserPreview>>
            val user1Api = UserPreview(
                id = "id.1",
                title = "mrs",
                firstName = "First",
                lastName = "User",
                picture = "some Url"
            )
            val usersApi = listOf(user1Api)
            usersApiResponse = Response.success(Page(data = usersApi, page = 1u, total = 1u))

            val user1Domain = user1Api.toDomain()
            val usersDomain = listOf(user1Domain)

            coEvery { api.userService.getUsers(1u) } returns usersApiResponse

            presenter.getUsers()

            coVerify(exactly = 1) { api.userService.getUsers(1u) }
            coVerify(exactly = 1) { view.displayUsers(usersDomain) }
            coVerify(exactly = 0) { view.displayError() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `error getting users then display that error`() {
        coroutinesTestRule.testDispatcher.run() {

            val usersResponse: Response<Page<UserPreview>> =
                Response.error(400, ResponseBody.create(MediaType.parse(""), ""))
            coEvery { api.userService.getUsers(1u) } returns usersResponse

            presenter.getUsers()

            coVerify(exactly = 1) { api.userService.getUsers(1u) }
            coVerify(exactly = 1) { view.displayError() }
        }
    }

    @After
    fun tearDown() {
        presenter.detach()
    }

}
