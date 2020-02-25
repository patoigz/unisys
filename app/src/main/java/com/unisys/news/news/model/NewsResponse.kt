package com.unisys.news.news.model

import com.google.gson.annotations.SerializedName
import com.unisys.news.news.repo.dto.Article

class NewsResponse {

    @SerializedName("totalResults")
    var totalResults: Int = 0

    @SerializedName("articles")
    var articles: List<Article> = emptyList()

    @SerializedName("status")
    var status: String? = null
}