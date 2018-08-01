package com.rq.practice.fragments.test;

import android.support.v4.app.Fragment;

import com.rq.practice.R;
import com.rq.practice.fragments.base.BaseFragment;

public class Fragment03 extends BaseFragment {

    @Override
    protected Fragment createFragment() {
        return new Fragment03();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_03;
    }

    @Override
    public void bindView() {

    }

    @Override
    public void initData() {

    }
}
