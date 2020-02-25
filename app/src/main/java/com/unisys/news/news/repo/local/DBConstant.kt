package com.unisys.news.news.repo.local

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
interface DBConstant {

    companion object {
        const val DB_NAME = "database_first_db"
        const val NEWS_TABLE_NAME = "news_table"

        // News table fields
        const val NEWS = "news"
        const val NEWS_ID = "id"
        const val NEWS_AUTHOR = "author"
        const val NEWS_TITLE = "title"
        const val NEWS_URL_IMAGE = "urlToImage"
        const val NEWS_CONTENT = "content"
        const val NEWS_DESCRIPTION = "description"
        const val NEWS_PUBLISHED_AT = "publishedAt"
        const val NEWS_URL = "url"
    }
}