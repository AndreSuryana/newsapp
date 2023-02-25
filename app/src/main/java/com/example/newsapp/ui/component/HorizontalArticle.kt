package com.example.newsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Ext.formatDateToDaysAgo

@Composable
fun HorizontalArticle(
    article: Article,
    modifier: Modifier = Modifier,
    onItemClick: (Article) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClick(article) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(90.dp)
                .aspectRatio(4f / 3f)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = stringResource(id = R.string.bullet_point),
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = article.publishedAt.formatDateToDaysAgo(),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
            }
        }
    }
}