package com.unisys.news.news.repo.remote

import com.unisys.news.base.remote.BaseRemote
import com.unisys.news.base.remote.RemoteConfiguration
import com.unisys.news.news.model.NewsResponse
import io.reactivex.Observable

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988></patriciogarcia1988>@gmail.com> on 02/24/2020.
 */
class NewsRemoteRepoImpl : BaseRemote(), NewsRemoteRepo {

    override val allNews: Observable<NewsResponse>
        get() = create(
            NewsServices::class.java,
            RemoteConfiguration.BASE_URL
        ).news("Box Repsol MotoGP")

    override fun topHeadlines(query: String): Observable<NewsResponse> {
        return create(
            NewsServices::class.java,
            RemoteConfiguration.BASE_URL
        ).topHeadlines(query)
    }
}