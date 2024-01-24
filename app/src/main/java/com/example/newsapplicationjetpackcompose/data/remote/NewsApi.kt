package com.example.newsapplicationjetpackcompose.data.remote

import com.example.newsapplicationjetpackcompose.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
   // GET
    // https://newsapi.org/v2/top-headlines?country=us&apiKey=f6e4d423debe4e8982b9c6eb2e3f6c61


    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse


    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "f6e4d423debe4e8982b9c6eb2e3f6c61"
    }
}