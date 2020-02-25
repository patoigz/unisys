package com.unisys.news.base.presenter;

import com.unisys.news.base.view.MvpView;

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 14/05/2017.
 */
public interface MvpPresenter<P extends MvpView> {

    /**
     * Called when an {@code MvpView} is attached to this presenter.
     *
     * @param view The attached {@code MvpView}
     */
    void onAttach(P view);

    /**
     * Called when the view is resumed according to Android components
     * NOTE: this method will only be called for presenters that override it.
     */
    void onResume();

    /**
     * Called when an {@code MvpView} is detached from this presenter.
     */
    void onDetach();

}
