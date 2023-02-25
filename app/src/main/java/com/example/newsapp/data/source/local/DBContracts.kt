package com.example.newsapp.data.source.local

object DBContracts {

    // Database name
    const val DATABASE_NAME = "news_database"

    object BookmarkColumns {
        const val TABLE_NAME = "bookmarks"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_SOURCE_ID = "source_id"
        const val COLUMN_SOURCE_NAME = "source_name"
        const val COLUMN_ARTICLE_URL = "article_url"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_PUBLISHED_AT = "published_at"
    }
}