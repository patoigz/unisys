package com.unisys.news.news.view

import com.unisys.news.base.view.MvpView
import com.unisys.news.news.model.NewsResponse

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
interface NewsView : MvpView {

    fun showNews(news: NewsResponse)

    fun showLoading()

    fun hideLoading()

    fun showError(error: String?)
}
