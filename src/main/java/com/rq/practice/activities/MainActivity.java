package com.rq.practice.activities;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rq.practice.R;
import com.rq.practice.activities.base.BaseActivity;
import com.rq.practice.activities.practice.CustomScrollActivity;
import com.rq.practice.activities.practice.FragmentTabHostPractice;
import com.rq.practice.adapter.MainAdapter;
import com.rq.practice.adapter.base.IAdapterChildClickListener;
import com.rq.practice.bean.PracticeBean;
import com.rq.practice.utils.EasyLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    private MainAdapter mMainAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mMainAdapter = new MainAdapter(MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView = findViewById(R.id.show_practice_list);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.setItemClickListener(itemClickListener);
        mMainAdapter.setItemChildClickListener(itemChildClickListener);
    }

    @Override
    public void initData() {
        List<PracticeBean> listData = new ArrayList<>();
        listData.add(createPracticeBean(FragmentTabHostPractice.class));
        listData.add(createPracticeBean(CustomScrollActivity.class));
        mMainAdapter.setListData(listData);
    }

    private PracticeBean createPracticeBean(Class<? extends Activity> clazz){
        PracticeBean practiceBean = new PracticeBean();
        practiceBean.setClazz(clazz);
        String className = clazz.getName();
        className = className.substring(className.lastIndexOf('.')+1);
        practiceBean.setText("跳转到"+className);
        return practiceBean;
    }

    // Item的监听回调
    MainAdapter.OnItemClickListener<MainAdapter> itemClickListener = new MainAdapter.OnItemClickListener<MainAdapter>() {
        @Override
        public void onItemClick(MainAdapter adapter, View itemView, int position) {
            Toast.makeText(MainActivity.this, "itemView命中!", Toast.LENGTH_SHORT).show();
        }
    };

    // Item的子View的监听回调
    IAdapterChildClickListener.OnItemChildClickListener<MainAdapter> itemChildClickListener =
            new IAdapterChildClickListener.OnItemChildClickListener<MainAdapter>(){

        @Override
        public void onItemChildClick(MainAdapter adapter, View view, int position) {
            PracticeBean practiceBean = adapter.getItemData(position);
            Class<? extends Activity> clazz = practiceBean.getClazz();
            startActivity(clazz);
        }
    };

    /**
     * 演示getDir操作
     * 返回/data/data/YouPackageName/下的指定名称的文件夹File对象.
     * 如果该文件夹不存在则用指定名称创建一个新的文件夹
     */
    public void testCache(){
        String path = getDir("test", MODE_PRIVATE).getAbsolutePath();
        ObjectOutputStream objectOutput = null;
        try {
            objectOutput = new ObjectOutputStream(new FileOutputStream(new File(path, "my_data")));
            objectOutput.writeInt(22);
            objectOutput.writeUTF("aaaa");
        } catch (IOException e) {
            e.printStackTrace();
            EasyLog.v("IOException::"+e.getMessage());
        }finally {
            if (objectOutput != null){
                try {
                    objectOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        ObjectInputStream objectInput = null;
        try {
            objectInput = new ObjectInputStream(new FileInputStream(new File(path, "my_data")));
            int intData = objectInput.readInt();
            String str = objectInput.readUTF();
            EasyLog.v("intData::"+intData);
            EasyLog.v("str::"+str);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (objectInput != null){
                try {
                    objectInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        EasyLog.v(path);
    }
}
