package com.thefork.challenge.user.module

import com.thefork.challenge.routes.UserScreenRouteContract
import com.thefork.challenge.user.router.UserScreenRouteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RouterModule {

    @Singleton
    @Provides
    fun providesUserScreenRouteContract(): UserScreenRouteContract = UserScreenRouteImpl()
}