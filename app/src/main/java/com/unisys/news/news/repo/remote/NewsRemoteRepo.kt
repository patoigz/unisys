package com.unisys.news.news.repo.remote

import com.unisys.news.news.model.NewsResponse
import io.reactivex.Observable

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988></patriciogarcia1988>@gmail.com> on 02/24/2020.
 */
interface NewsRemoteRepo {

    val allNews: Observable<NewsResponse>

    fun topHeadlines(query: String): Observable<NewsResponse>
}