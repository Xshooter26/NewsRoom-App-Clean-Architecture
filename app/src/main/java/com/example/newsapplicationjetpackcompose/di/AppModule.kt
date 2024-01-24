package com.example.newsapplicationjetpackcompose.di

import com.example.newsapplicationjetpackcompose.data.remote.NewsApi
import com.example.newsapplicationjetpackcompose.data.remote.NewsApi.Companion.BASE_URL
import com.example.newsapplicationjetpackcompose.data.repository.NewsRepositoryImpl
import com.example.newsapplicationjetpackcompose.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi() : NewsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi) : NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }

}