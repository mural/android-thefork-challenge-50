package com.thefork.challenge.user.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.thefork.challenge.domain.LoadStatus
import com.thefork.challenge.domain.User
import com.thefork.challenge.user.CoroutineTestRule
import com.thefork.challenge.user.LifeCycleTestOwner
import com.thefork.challenge.user.TestableObserver
import com.thefork.challenge.user.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class UserViewModelTest {

    private var repository = mockk<UserRepository>()
    private lateinit var viewModel: UserViewModel
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private lateinit var userObserver: Observer<LoadStatus<User>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        userObserver = mockk()

        viewModel =
            UserViewModel(
                userRepository = repository,
                dispatcherMain = coroutinesTestRule.testDispatcher,
                dispatcherIO = coroutinesTestRule.testDispatcher
            )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get user data successfully`() {
        coroutinesTestRule.testDispatcher.run() {

            val user1Full = User(
                id = "id.1",
                title = "mrs",
                firstName = "First",
                lastName = "User",
                gender = "female",
                email = "some@mail.com",
                dateOfBirth = "12/12/1999",
                registerDate = "12/12/2012",
                phone = "33-213-2183111",
                picture = "some Url"
            )

            viewModel.response.observe(lifeCycleTestOwner, userObserver)

            val stateObserver = TestableObserver<LoadStatus<User>>()
            viewModel.response.apply {
                observeForever(stateObserver)
            }

            coEvery { repository.getUser("id.1") } returns user1Full

            viewModel.init("id.1")

            val loadingResult = LoadStatus.Loading<User>()
            val successResult = LoadStatus.Success(user1Full)

            stateObserver.assertAllEmitted(listOf(loadingResult, successResult))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `error getting user data`() {
        coroutinesTestRule.testDispatcher.run() {

            viewModel.response.observe(lifeCycleTestOwner, userObserver)

            val stateObserver = TestableObserver<LoadStatus<User>>()
            viewModel.response.apply {
                observeForever(stateObserver)
            }

            coEvery { repository.getUser("id.1") } throws Exception()

            viewModel.init("id.1")

            val loadingResult = LoadStatus.Loading<User>()
            val errorResult = LoadStatus.Error<User>(message = "")

            stateObserver.assertAllEmitted(listOf(loadingResult, errorResult))
        }
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

}