package com.unisys.news.base.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.unisys.news.base.presenter.MvpPresenter;

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 14/05/2017.
 */
public abstract class BaseActivity<T extends MvpPresenter> extends AppCompatActivity implements MvpView {

    private T presenter;

    protected
    @NonNull
    T getPresenter() {
        if (presenter == null)
            presenter = createPresenter();
        if (presenter == null)
            throw new IllegalStateException("createPresenter() implementation returns null!");
        return presenter;
    }

    protected abstract T createPresenter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onAttach(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDetach();
    }
}