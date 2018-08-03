package com.rq.practice.framework.presenter;

import com.rq.practice.framework.view.BaseView;

/**
 * 通用Presenter
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public abstract class CommonPresenter<V extends BaseView> implements BasePresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
