package com.example.newsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.util.Ext.formatDateToDaysAgo

@Composable
fun VerticalArticle(
    article: Article,
    modifier: Modifier = Modifier,
    onItemClick: (Article) -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onItemClick(article) }
            .padding(16.dp)
            .background(Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(16.dp))
                    .weight(1f),
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = article.source.name,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
            Text(
                text = article.publishedAt.formatDateToDaysAgo(),
                style = MaterialTheme.typography.subtitle1,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
                maxLines = 1
            )
        }
        Text(
            text = article.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}