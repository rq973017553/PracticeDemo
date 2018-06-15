package com.rq.practice.activities.practice;

import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.rq.practice.R;
import com.rq.practice.activities.MainActivity;
import com.rq.practice.activities.base.BaseActivity;
import com.rq.practice.fragments.test.Fragment00;
import com.rq.practice.fragments.test.Fragment01;
import com.rq.practice.fragments.test.Fragment02;
import com.rq.practice.fragments.test.Fragment03;
import com.rq.practice.fragments.test.Fragment04;
import com.rq.practice.utils.EasyLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  FragmentTabHost练习类
 * @author rock you
 * @version 1.0.0
 */
public class FragmentTabHostPractice extends BaseActivity {

    private static final int SIZE = 5;

    private static final String text[] = {"首页", "赛事", "会员", "直播", "用户"};

    private static final int ima[] = {
            R.drawable.tab_home,
            R.drawable.tab_live,
            R.drawable.tab_vip,
            R.drawable.tab_isliving,
            R.drawable.tab_user};

    private static final Class fragments[] = {
            Fragment00.class,
            Fragment01.class,
            Fragment02.class,
            Fragment03.class,
            Fragment04.class};

    private FragmentTabHost mTabHost;

    @Override
    public int getLayoutID() {
        return R.layout.activity_fragment_tab_host;
    }

    @Override
    public void initView() {
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
        for (int i = 0; i < SIZE; i ++){
            TabHost.TabSpec tabSpec = createTabSpec(text[i], ima[i]);
            mTabHost.addTab(tabSpec, fragments[i], null);
        }
        mTabHost.setCurrentTab(0);
    }

    @Override
    public void initData() {
    }

    private TabHost.TabSpec createTabSpec(final String text, int imaRes){
        View view = mLayoutInflater.inflate(R.layout.activity_tab, null);
        TextView textView = view.findViewById(R.id.tab_text);
        textView.setText(text);
        ImageView imageView = view.findViewById(R.id.tab_ima);
        imageView.setImageResource(imaRes);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(text);
        tabSpec.setIndicator(view);
        return tabSpec;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(FragmentTabHostPractice.this, "Setting", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
