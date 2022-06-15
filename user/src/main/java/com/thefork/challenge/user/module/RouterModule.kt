package com.thefork.challenge.user.module

import com.mural.common.UserScreenRouteContract
import com.thefork.challenge.user.UserScreenRouteImpl
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