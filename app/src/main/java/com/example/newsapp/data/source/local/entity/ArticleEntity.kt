package com.example.newsapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.model.Source
import com.example.newsapp.data.source.local.DBContracts.BookmarkColumns
import com.example.newsapp.util.Ext.toDate

@Entity(tableName = BookmarkColumns.TABLE_NAME)
data class ArticleEntity(

    @ColumnInfo(name = BookmarkColumns.COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = BookmarkColumns.COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_AUTHOR)
    val author: String? = null,

    @ColumnInfo(name = BookmarkColumns.COLUMN_DESCRIPTION)
    val description: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_CONTENT)
    val content: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_SOURCE_ID)
    val sourceId: String?,

    @ColumnInfo(name = BookmarkColumns.COLUMN_SOURCE_NAME)
    val sourceName: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_ARTICLE_URL)
    val articleUrl: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_IMAGE_URL)
    val imageUrl: String,

    @ColumnInfo(name = BookmarkColumns.COLUMN_PUBLISHED_AT)
    val publishedAt: String
) {
    fun toArticle(): Article {
        return Article(
            source = Source(sourceId, sourceName),
            author = author,
            title = title,
            description = description,
            articleUrl = articleUrl,
            content = content,
            imageUrl = imageUrl,
            publishedAt = publishedAt.toDate()
        )
    }
}
