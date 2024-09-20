package com.sandclan.examplemodularmvvmrecipes.di

import com.sandclan.examplemodularmvvmrecipes.navigation.NavigationSubGraphs
import com.sandclan.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(searchFeatureApi: SearchFeatureApi): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi)
    }
}