package com.example.newsapplicationjetpackcompose.ui.news_screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapplicationjetpackcompose.domain.model.Article
import com.example.newsapplicationjetpackcompose.presentation.component.BottomSheetContent
import com.example.newsapplicationjetpackcompose.presentation.component.CategoryTabRow
import com.example.newsapplicationjetpackcompose.presentation.component.NewsArticleCard
import com.example.newsapplicationjetpackcompose.presentation.component.NewsScreenTopBar
import com.example.newsapplicationjetpackcompose.presentation.component.RetryContent
import com.example.newsapplicationjetpackcompose.presentation.component.SearchBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun NewsScreen(
    state: NewsScreenState,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadFullStoryButtonPressed: (String?) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val categories =
        listOf("General", "business", "Health", "Sports", "Technology", "Entertainment")
    val pagerState = rememberPagerState(pageCount = {
        categories.size
    })
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var shouldBottomSheetShow by remember { mutableStateOf(false) }
//
//    val focusRequester = remember{FocusRequester()}
//    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current



    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState
        ) {
            state.selectedArticle?.let {
                BottomSheetContent(article = it, onReadFullStoryClicked = {
                    onReadFullStoryButtonPressed(it.url)
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) shouldBottomSheetShow = false
                    }
                })
            }
        }
    }

    //while scrolling the left and the right to load the news of various categories
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(NewsScreenEvent.OnCategoryChanged(category = categories[page]))
        }
    }
    LaunchedEffect(key1 = Unit) {
       if(state.searchQuery.isNotEmpty()){
           onEvent(NewsScreenEvent.OnSearchQueryChanged(searchQuery = state.searchQuery))
       }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = state.isSearchBarVisible, label = "") { isVisible ->
            if (isVisible) {
                Column {
                    SearchBar(
                        // modifier = Modifier.focusRequester(focusRequester),
                        value = state.searchQuery,
                        onValueChange = {

                                newValue ->

                            onEvent(NewsScreenEvent.OnSearchQueryChanged(newValue))
                        },
                        onCloseIconClick = {
                            onEvent(NewsScreenEvent.OnCloseIconClicked)
                        },
                        onSearchIconClicked = {
                            keyboardController?.hide()
                            //          focusManager.clearFocus()
                        }
                    )
                    NewsArticleList(state = state, onCardClicked = { article ->
                        shouldBottomSheetShow = true
                        onEvent(NewsScreenEvent.OnNewsCardClicked(article = article)) //Whateveer card the user has Clicked we will pass in that event
                    },
                        onRetry = {
                            onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                        })
                }
            } else {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        NewsScreenTopBar(
                            scrollBehavior = scrollBehavior,
                            onSearchIconClicked = {
                                onEvent(NewsScreenEvent.OnSearchIconClicked)

                                //    focusRequester.requestFocus()

                            })
                    }
                ) { paddingValues ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        CategoryTabRow(
                            pagerState = pagerState,
                            categories = categories,
                            onTabSelected = { index ->
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            })

                        HorizontalPager(state = pagerState) {
                            NewsArticleList(state = state, onCardClicked = { article ->
                                shouldBottomSheetShow = true
                                onEvent(NewsScreenEvent.OnNewsCardClicked(article = article)) //Whateveer card the user has Clicked we will pass in that event
                            },
                                onRetry = {
                                    onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                                })
                        }
                    }

                }
            }
        }
    }


}


@Composable
fun NewsArticleList(
    state: NewsScreenState,
    onCardClicked: (Article) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),

        ) {
        items(state.articles) { article ->
            NewsArticleCard(
                article = article, onCardClick = onCardClicked
            )

        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            RetryContent(error = state.error, onRetry = onRetry)
        }
    }
}
























