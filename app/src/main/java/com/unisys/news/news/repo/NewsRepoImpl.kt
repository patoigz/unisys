package com.unisys.news.news.repo

import com.unisys.news.news.repo.local.NewsLocalRepo
import com.unisys.news.news.model.NewsResponse
import com.unisys.news.news.repo.remote.NewsRemoteRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
class NewsRepoImpl(var remoteNewsRepo: NewsRemoteRepo, var localNewsRepo: NewsLocalRepo) :
    NewsRepo {

    override fun getAllNews(): Observable<Any>? {
        return Observable.mergeDelayError(
            remoteNewsRepo.allNews
                .doOnNext({ response -> localNewsRepo.addNews(response.articles) })
                .doOnError({ localNewsRepo.allArticles })
                .subscribeOn(Schedulers.io()), localNewsRepo.allArticles
                .subscribeOn(Schedulers.io())
        )
    }

    override fun getTopHeadlines(query: String): Observable<NewsResponse>? {
        return remoteNewsRepo
            .topHeadlines(query)
            .subscribeOn(Schedulers.io())
    }
}