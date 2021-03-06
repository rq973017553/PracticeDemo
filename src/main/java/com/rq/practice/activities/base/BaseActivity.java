package com.rq.practice.activities.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.rq.practice.R;
import com.rq.practice.utils.ActivityUtils;
import com.rq.practice.utils.SystemBarUtils;

/**
 * BaseActivity
 * Activity基类
 *
 * @author rock you
 * @version [1.0.0 2018.6.1]
 */
public abstract class BaseActivity extends AppCompatActivity {

    // R.id.toolbar
    protected Toolbar mToolBar;

    // R.id.add_layout
    protected FrameLayout mAddLayout;

    // LayoutInflater
    protected LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        initTemplateLayout();
        ActivityUtils.onActivityCreate(this);
        bindView();
        initData();
    }

    private void initTemplateLayout(){
        mLayoutInflater = LayoutInflater.from(this);

        // ToolBar
        mToolBar = findViewById(R.id.toolbar);
        initToolBar();

        // 添加类
        mAddLayout = findViewById(R.id.add_layout);
        int layoutID = getLayoutID();
        View view = mLayoutInflater.inflate(layoutID, null);
        if (view == null){
            throw new RuntimeException("inflter "+layoutID+"failure!");
        }
        mAddLayout.addView(view, new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initToolBar(){
        setTitle("");
        mToolBar.setTitle("");
        if (mToolBar != null){
            setSupportActionBar(mToolBar);
//            SystemBarUtils.setAndroidNativeLightStatusBar(mToolBar, false);
            SystemBarUtils.setStatusBarFullTransparent(getWindow(), false);
//            SystemBarUtils.setHalfTransparent(getWindow());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityUtils.onActivityStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityUtils.onActivityResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityUtils.onActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityUtils.onActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.onActivityDestroy(this);
    }

    protected void startActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(this, clazz));
    }

    public abstract int getLayoutID();

    public abstract void bindView();

    public abstract void initData();
}
