package com.rq.practice.activities;

import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.rq.practice.R;
import com.rq.practice.activities.base.BaseActivity;
import com.rq.practice.utils.EasyLog;
import com.rq.practice.view.CustomHorizontalScrollView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends BaseActivity {

    private CustomHorizontalScrollView mCustomView;

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        mCustomView = findViewById(R.id.custom_horizontal_scrollView);
    }

    // 获取View的宽高
    public void testViewSize(){
        // 1.获取View的宽高的一种方法。使用该方案的时候，建议放在onAttachedToWindow方法中
        mCustomView.post(new Runnable() {
            @Override
            public void run() {
                EasyLog.v("Width::"+ mCustomView.getWidth());
                EasyLog.v("Height::"+ mCustomView.getHeight());
            }
        });

        // 2.获取View的宽高的一种方法
        final ViewTreeObserver observer = mCustomView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                EasyLog.v("Width::"+ mCustomView.getWidth());
                EasyLog.v("Height::"+ mCustomView.getHeight());
                // 注意isAlive一定要判断
                if (observer.isAlive()){
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
                        // 安卓版本在16以上使用
                        observer.removeOnGlobalLayoutListener(this);
                    }else {
                        // 安卓版本在16以下使用
                        observer.removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
    }

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

//    private void initToolBar(){
//        setTitle("");
//        mToolBar = findViewById(R.id.toolbar);
//        mToolBar.setTitle("");
//        setSupportActionBar(mToolBar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }

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
            Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


}
