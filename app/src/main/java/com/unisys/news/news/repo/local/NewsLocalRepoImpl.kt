package com.unisys.news.news.repo.local

import com.unisys.news.news.repo.dto.Article
import io.reactivex.Observable

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988></patriciogarcia1988>@gmail.com> on 02/24/2020.
 */
class NewsLocalRepoImpl(private val newsDao: NewsDao) : NewsLocalRepo {

    override val allArticles: Observable<List<Article>>
        get() = Observable.fromCallable<List<Article>>({ newsDao.all() })

    override fun addNews(news: List<Article>) {
        newsDao.insertAll(news)
    }
}