package com.rq.practice.fragments.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseLazyLoadFragment extends BaseFragment{

    private boolean isCreateView = false;

    private boolean isShowUI = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isCreateView = true;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initData() {
        if (isShowUI && isCreateView){
            lazyLoad();
            isCreateView = false;
            isShowUI = false;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            isShowUI = true;
            initData();
        }else {
            isShowUI = false;
        }
    }

    public abstract void lazyLoad();
}
