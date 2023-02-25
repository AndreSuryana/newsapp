package com.example.newsapp.util

import androidx.annotation.DrawableRes
import com.example.newsapp.R

enum class NewsCategory(
    val keyword: String,
    @DrawableRes val imageRes: Int
) {
    TECHNOLOGY("technology", R.drawable.category_technology),
    BUSINESS("business", R.drawable.category_business),
    SPORTS("sports", R.drawable.category_sports),
    SCIENCE("science", R.drawable.category_science),
    HEALTH("health", R.drawable.category_health),
    TRAVELLING("travelling", R.drawable.category_travelling),
    ENTERTAINMENT("entertainment", R.drawable.category_entertainment)
}