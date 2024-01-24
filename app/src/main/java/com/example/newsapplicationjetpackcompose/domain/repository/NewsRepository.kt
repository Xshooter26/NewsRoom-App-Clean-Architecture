package com.example.newsapplicationjetpackcompose.domain.repository

import com.example.newsapplicationjetpackcompose.domain.model.Article
import com.example.newsapplicationjetpackcompose.utils.Resource

interface NewsRepository {
    suspend fun  getTopHeadlines(
        category : String
    ): Resource<List<Article>>

    suspend fun  searchForNews(
        query : String
    ): Resource<List<Article>>
}