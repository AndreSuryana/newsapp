package com.example.newsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.util.NewsCategory

@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    categories: List<NewsCategory> = NewsCategory.values().toList(),
    selectedCategory: NewsCategory,
    onCategorySelected: (NewsCategory) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = contentPadding
    ) {
        itemsIndexed(items = categories) { idx, category ->
            val isSelected = category == selectedCategory
            CategoryItem(
                modifier = Modifier
                    .clip(CircleShape)
                    .selectable(
                        selected = category == selectedCategory,
                        onClick = {
                            onCategorySelected(category)
                        }
                    )
                    .border(width = 2.dp, color = Color.Transparent, shape = CircleShape)
                    .border(
                        width = if (isSelected) 4.dp else 0.dp,
                        color = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
                        shape = CircleShape
                    ),
                category = category
            )
            if (idx != categories.lastIndex) {
                Divider(
                    modifier = Modifier.width(12.dp),
                    color = Color.Transparent
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: NewsCategory,
    size: Dp = 90.dp
) {
    Box(
        modifier = modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.keyword,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = Color(0x73000000), blendMode = BlendMode.SrcOver)
        )
        Text(
            text = category.keyword.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.h4,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}