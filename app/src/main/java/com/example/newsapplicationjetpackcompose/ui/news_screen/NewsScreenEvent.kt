package com.example.newsapplicationjetpackcompose.ui.news_screen

import com.example.newsapplicationjetpackcompose.domain.model.Article

sealed class NewsScreenEvent{

    data class OnNewsCardClicked(var article: Article) : NewsScreenEvent()
    data class OnCategoryChanged(var category: String) : NewsScreenEvent()
data class OnSearchQueryChanged(var searchQuery: String) : NewsScreenEvent()
object OnSearchIconClicked: NewsScreenEvent()
object OnCloseIconClicked: NewsScreenEvent()
}
