package com.example.newsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.source.local.dao.BookmarkDao
import com.example.newsapp.data.source.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class NewsAppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}