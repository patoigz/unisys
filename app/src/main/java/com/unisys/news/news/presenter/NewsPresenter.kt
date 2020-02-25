package com.unisys.news.news.presenter

import com.unisys.news.base.presenter.BasePresenter
import com.unisys.news.news.view.NewsView

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
abstract class NewsPresenter : BasePresenter<NewsView>() {

    abstract val getNews: Unit

    abstract fun queryTopHeadlines(query: String)
}
