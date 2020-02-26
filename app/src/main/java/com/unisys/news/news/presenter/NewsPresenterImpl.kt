package com.unisys.news.news.presenter

import com.unisys.news.news.model.NewsResponse
import com.unisys.news.news.repo.NewsRepo
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver


/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
internal class NewsPresenterImpl(newssRep: NewsRepo, scheduler: Scheduler) : NewsPresenter() {

    private val newsRepo: NewsRepo
    private val scheduler: Scheduler
    private var disposable: Disposable? = null

    init {
        this.newsRepo = newssRep
        this.scheduler = scheduler
    }

    override val getNews: Unit
        get() {
            if (!isViewAttached) return
            view!!.showLoading()
            disposable = newsRepo
                .getAllNews()
                ?.observeOn(scheduler)
                ?.subscribeWith(object : DisposableObserver<Any>() {
                    override fun onNext(obj: Any) {
                        if (!isViewAttached) return
                        view!!.showNews(obj as NewsResponse)
                    }

                    override fun onError(ex: Throwable) {
                        if (!isViewAttached) return
                        view!!.showError(ex.localizedMessage)
                    }

                    override fun onComplete() {
                    }
                })
        }

    override fun queryTopHeadlines(query: String) {
        if (!isViewAttached) return
        view!!.showLoading()
        disposable = newsRepo
            .getTopHeadlines(query)
            ?.observeOn(scheduler)
            ?.subscribeWith(object : DisposableObserver<Any>() {
                override fun onNext(obj: Any) {
                    if (!isViewAttached) return
                    view!!.showNews(obj as NewsResponse)
                }

                override fun onError(ex: Throwable) {
                    if (!isViewAttached) return
                    view!!.showError(ex.localizedMessage)
                }

                override fun onComplete() {
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        disposable!!.dispose()
    }
}
