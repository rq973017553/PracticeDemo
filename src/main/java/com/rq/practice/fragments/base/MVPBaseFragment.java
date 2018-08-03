package com.rq.practice.fragments.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rq.practice.framework.presenter.BasePresenter;
import com.rq.practice.framework.view.BaseView;

/**
 * 带MVP的BaseFragment
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public abstract class MVPBaseFragment<P extends BasePresenter> extends BaseFragment implements BaseView{

    protected P mPresenter;

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // 暂时这样写，后续加入Dagger2
        if (mPresenter == null){
            throw new RuntimeException("presenter is null!");
        }
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }
}
