package com.example.newsapplicationjetpackcompose.utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsapplicationjetpackcompose.presentation.article_screen.ArticleScreen
import com.example.newsapplicationjetpackcompose.ui.news_screen.NewsScreen
import com.example.newsapplicationjetpackcompose.ui.news_screen.NewsScreenViewModel

@Composable
fun NavGraph(
    navController: NavHostController
){

    val argKey = "web_url"
    NavHost(navController = navController, startDestination = "news_screen"){
        composable(route = "news_screen"){
            val viewModel : NewsScreenViewModel = hiltViewModel()
            NewsScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullStoryButtonPressed = {
                    url ->
                    navController.navigate("article_screen?$argKey=$url")

                }
            )
        }

        composable(route = "article_screen?$argKey={$argKey}",         //Don't Give unnecessary spaces here
            arguments = listOf(navArgument(name = argKey){
                type = NavType.StringType
            })){backStackEntry ->
            ArticleScreen(url = backStackEntry.arguments?.getString(argKey),
                onBackPressed = {navController.navigateUp()})
        }

    }

}