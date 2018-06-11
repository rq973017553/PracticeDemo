package com.rq.practice.fragments;

import android.support.v4.app.Fragment;

import com.rq.practice.fragments.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    @Override
    public int getLayoutID() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected Fragment createFragment() {
        return new HomeFragment();
    }
}
