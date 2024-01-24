package com.example.newsapplicationjetpackcompose.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.newsapplicationjetpackcompose.domain.model.Article
import com.example.newsapplicationjetpackcompose.ui.theme.Pink40
import com.example.newsapplicationjetpackcompose.utils.dateFormatter

@Composable
fun NewsArticleCard(
    modifier:
    Modifier = Modifier,
    article: Article,
    onCardClick: (Article) -> Unit
) {


    val date = dateFormatter(article.publishedAt)
    Card(modifier = Modifier.background(Pink40)
        .clickable { onCardClick(article)}
        ) {

       Column(modifier = Modifier.padding(15.dp)) {
            ImageHolder(imageUrl = article.urlToImage)
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source.name ?: "",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }

}