package com.rq.practice.fragments.test;

import android.support.v4.app.Fragment;

import com.rq.practice.R;
import com.rq.practice.fragments.base.BaseFragment;

public class Fragment00 extends BaseFragment {

    @Override
    protected Fragment createFragment() {
        return new Fragment00();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_00;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
