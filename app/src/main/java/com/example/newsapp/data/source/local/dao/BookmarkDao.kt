package com.example.newsapp.data.source.local.dao

import androidx.room.*
import com.example.newsapp.data.source.local.DBContracts.BookmarkColumns
import com.example.newsapp.data.source.local.entity.ArticleEntity

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM ${BookmarkColumns.TABLE_NAME} ORDER BY ${BookmarkColumns.COLUMN_ID} DESC")
    fun getAllArticles(): List<ArticleEntity>

    @Insert(ArticleEntity::class)
    fun insertArticle(articleEntity: ArticleEntity)

    @Delete(ArticleEntity::class)
    fun removeArticle(articleEntity: ArticleEntity)

    @Query("SELECT EXISTS(SELECT * FROM ${BookmarkColumns.TABLE_NAME} WHERE ${BookmarkColumns.COLUMN_ARTICLE_URL} = :url)")
    fun isArticleBookmarked(url: String): Boolean
}