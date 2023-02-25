package com.example.newsapp.data.model

import com.example.newsapp.data.source.local.entity.ArticleEntity
import com.example.newsapp.util.Ext.formatDate
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Article(

    @SerializedName("source")
    val source: Source,

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("url")
    val articleUrl: String,

    @SerializedName("urlToImage")
    val imageUrl: String,

    @SerializedName("publishedAt")
    val publishedAt: Date
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            title = title,
            author = author,
            description = description,
            content = content,
            sourceId = source.id,
            sourceName = source.name,
            articleUrl = articleUrl,
            imageUrl = imageUrl,
            publishedAt = publishedAt.formatDate()
        )
    }
}
