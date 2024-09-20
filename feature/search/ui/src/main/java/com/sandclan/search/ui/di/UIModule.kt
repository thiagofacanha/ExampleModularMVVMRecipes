package com.sandclan.search.ui.di

import com.sandclan.search.ui.navigation.SearchFeatureApi
import com.sandclan.search.ui.navigation.SearchFeatureApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UIModule {

    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi {
        return SearchFeatureApiImpl()

    }

}