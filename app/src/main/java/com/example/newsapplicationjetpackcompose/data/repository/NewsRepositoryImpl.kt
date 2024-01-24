package com.example.newsapplicationjetpackcompose.data.repository

import com.example.newsapplicationjetpackcompose.data.remote.NewsApi
import com.example.newsapplicationjetpackcompose.domain.model.Article
import com.example.newsapplicationjetpackcompose.domain.repository.NewsRepository
import com.example.newsapplicationjetpackcompose.utils.Resource

class NewsRepositoryImpl(
    private val newsApi : NewsApi
) : NewsRepository{
    override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {

        return try{
            val response = newsApi.getNews(category = category)
            Resource.Success(response.articles)
        }
        catch (e: Exception){
            Resource.Error(message = " Failed to fetch the News ${e.message}")
        }
    }

    override suspend fun searchForNews(query: String): Resource<List<Article>> {
        return try{
            val response = newsApi.searchForNews(query = query)
            Resource.Success(response.articles)
        }
        catch (e: Exception){
            Resource.Error(message = " Failed to fetch the News ${e.message}")
        }
    }

}