package com.rq.practice.bean;

import android.app.Activity;

/**
 * PracticeBean
 * @author rock you
 * @version 1.0.0
 */
public class PracticeBean {

    private Class<? extends Activity> mClazz;

    private String mText;

    public Class<? extends Activity> getClazz() {
        return mClazz;
    }

    public void setClazz(Class<? extends Activity> mClazz) {
        this.mClazz = mClazz;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }
}
