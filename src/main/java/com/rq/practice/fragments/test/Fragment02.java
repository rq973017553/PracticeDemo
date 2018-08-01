package com.rq.practice.fragments.test;

import android.support.v4.app.Fragment;

import com.rq.practice.R;
import com.rq.practice.fragments.base.BaseFragment;

public class Fragment02 extends BaseFragment {

    @Override
    protected Fragment createFragment() {
        return new Fragment02();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_02;
    }

    @Override
    public void bindView() {

    }

    @Override
    public void initData() {

    }
}
