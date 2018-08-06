package com.rq.practice.fragments.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 *  BaseFragment
 *  Fragment基类
 *
 * @author rock you
 * @version [1.0.0 2018.6.4]
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(getLayoutID(), container, false);
            bindView();
            initData();
        }

        ViewParent viewParent = mView.getParent();
        if (viewParent != null){
            // 防止多次加载View
            ((ViewGroup)viewParent).removeView(mView);
        }
        return mView;
    }

    public Fragment newInstance(Bundle bundle){
        Fragment fragment = createFragment();
        if (bundle != null){
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public Fragment newInstance(){
        return newInstance(null);
    }

    protected abstract Fragment createFragment();

    public abstract int getLayoutID();

    public abstract void bindView();

    public abstract void initData();

    private View getCurrentView(){
        return mView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id){
        T t = getCurrentView().findViewById(id);
        if (t == null){
            throw new IllegalArgumentException("id not found!");
        }
        return (T) t.findViewById(id);
    }

    protected void startActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(getActivity(), clazz));
    }

}
