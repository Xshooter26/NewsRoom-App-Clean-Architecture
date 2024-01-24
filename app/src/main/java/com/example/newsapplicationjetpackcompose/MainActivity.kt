package com.example.newsapplicationjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsapplicationjetpackcompose.presentation.theme.NewsApplicationJetpackComposeTheme
import com.example.newsapplicationjetpackcompose.ui.news_screen.NewsScreen
import com.example.newsapplicationjetpackcompose.ui.news_screen.NewsScreenViewModel
import com.example.newsapplicationjetpackcompose.ui.theme.PurpleGrey80
import com.example.newsapplicationjetpackcompose.utils.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PurpleGrey80
                ) {

                    val navController = rememberNavController()
                    NavGraph(navController = navController)


                }

                }
            }
        }
    }



