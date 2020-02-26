package com.unisys.news.news.repo.remote

import com.unisys.news.news.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988></patriciogarcia1988>@gmail.com> on 02/24/2020.
 */
interface NewsServices {

    @GET("everything")
    fun news(@Query("q") q: String? = ""): Observable<NewsResponse>

    @GET("top-headlines")
    fun topHeadlines(@Query("q") query: String? = ""): Observable<NewsResponse>
}