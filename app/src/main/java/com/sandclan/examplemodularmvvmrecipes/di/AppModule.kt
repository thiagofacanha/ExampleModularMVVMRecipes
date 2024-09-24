package com.sandclan.examplemodularmvvmrecipes.di

import android.content.Context
import com.sandclan.examplemodularmvvmrecipes.local.AppDatabase
import com.sandclan.examplemodularmvvmrecipes.navigation.NavigationSubGraphs
import com.sandclan.search.data.local.RecipeDao
import com.sandclan.search.ui.navigation.SearchFeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideNavigationSubGraphs(searchFeatureApi: SearchFeatureApi): NavigationSubGraphs {
        return NavigationSubGraphs(searchFeatureApi)
    }


    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)


    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipeDao = appDatabase.recipeDao()
}