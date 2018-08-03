package com.rq.practice.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rq.practice.framework.presenter.BasePresenter;
import com.rq.practice.framework.view.BaseView;

/**
 * 带MVP架构的BaseActivity
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 暂时这样写，后续加入Dagger2
        if (mPresenter == null){
            throw new RuntimeException("presenter is null!");
        }
        mPresenter.attachView(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        // Empty Method
    }

    @Override
    public void showError() {
        // Empty Method
    }

    @Override
    public void showEmpty() {
        // Empty Method
    }

    @Override
    public void startLoading() {
        // Empty Method
    }

    @Override
    public void stopLoading() {
        // Empty Method
    }
}
