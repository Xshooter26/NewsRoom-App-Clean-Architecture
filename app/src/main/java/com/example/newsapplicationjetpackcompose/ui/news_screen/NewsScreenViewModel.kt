package com.example.newsapplicationjetpackcompose.ui.news_screen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplicationjetpackcompose.domain.model.Article
import com.example.newsapplicationjetpackcompose.domain.repository.NewsRepository
import com.example.newsapplicationjetpackcompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.http.Query
import javax.inject.Inject


@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(){

    var state by mutableStateOf(NewsScreenState())
    private var searchJob : Job? = null

    fun onEvent(event : NewsScreenEvent){
        when(event){
            is NewsScreenEvent.OnCategoryChanged -> {
               state = state.copy(category = event.category)
                getNewsArticles(state.category)
            }
            NewsScreenEvent.OnCloseIconClicked -> {
                state = state.copy(isSearchBarVisible = false)
                getNewsArticles(state.category)

            }
            is NewsScreenEvent.OnNewsCardClicked -> {
                state = state.copy(selectedArticle = event.article)
            }
            NewsScreenEvent.OnSearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true,
                    articles = emptyList()
                )
            }
            is NewsScreenEvent.OnSearchQueryChanged -> {
                state = state.copy(searchQuery = event.searchQuery)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000)         //we will only call this searchForNews function after one second after typing
                    searchForNews(query = state.searchQuery)
                }

            }



        }
    }



    private fun getNewsArticles(category: String){

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepository.getTopHeadlines(category = category)
            when(result){


                is Resource.Success -> {
                 state = state.copy(
                     articles  =result.data?: emptyList(),
                     isLoading = false,
                     error = null
                 )
                }

                is Resource.Error -> {
                    state = state.copy(
                        error =result.message,
                        isLoading = false,
                        articles = emptyList()

                    )
                }

            }
        }
    }

    private fun searchForNews(query: String){

        if(query.isEmpty()){
            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepository.searchForNews(query = query)
            when(result){


                is Resource.Success -> {
                    state = state.copy(
                        articles  =result.data?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        error =result.message,
                        isLoading = false,
                        articles = emptyList()

                    )
                }

            }
        }
    }




}