package com.rq.practice.framework.presenter;

import com.rq.practice.framework.view.BaseView;

/**
 * BasePresenter
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public interface BasePresenter<T extends BaseView> {

    // 绑定View
    void attachView(T view);

    // 解绑View
    void detachView();
}
