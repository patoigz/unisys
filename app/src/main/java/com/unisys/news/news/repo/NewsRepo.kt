package com.unisys.news.news.repo

import com.unisys.news.news.model.NewsResponse
import io.reactivex.Observable

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
interface NewsRepo {

    fun getAllNews(): Observable<Any>?

    fun getTopHeadlines(query: String): Observable<NewsResponse>?
}