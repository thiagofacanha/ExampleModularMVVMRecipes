package com.sandclan.search.data.di

import com.sandclan.search.data.remote.SearchApiService
import com.sandclan.search.data.repository.SearchRepositoryImpl
import com.sandclan.search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://www.themealdb.com/"

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }


    @Provides
    fun provideSearchRepository(searchApiService: SearchApiService): SearchRepository {
        return SearchRepositoryImpl(searchApiService)
    }

}