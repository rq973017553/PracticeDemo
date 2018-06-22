package com.rq.practice.adapter.base;

import android.view.View;

/**
 * 通用的Child监听接口集合
 * @author rock you
 * @version 1.0.0
 */
public interface IAdapterChildClickListener {

    ////////////////////////////////////////////监听接口////////////////////////////////////////////////////////////

    /**
     * 每个Item子View的click监听接口
     */
    interface OnItemChildClickListener<T> {
        /**
         *
         * @param adapter  T类型适配器
         * @param view     ItemView
         * @param position 点击的pos
         */
        void onItemChildClick(T adapter, View view, int position);
    }

    /**
     * 每个Item子View的longClick监听接口
     */
    interface OnItemChildLongClickListener<T> {
        /**
         *
         * @param adapter  T类型适配器
         * @param view     ItemView
         * @param position 点击的pos
         */
        boolean onItemChildLongClick(T adapter, View view, int position);
    }

    /**
     * 每个Item子View的touchClick监听接口
     */
    interface OnItemChildTouchClickListener<T> {
        /**
         *
         * @param adapter  T类型适配器
         * @param view     ItemView
         * @param position 点击的pos
         */
        boolean onItemChildTouchClick(T adapter, View view, int position);
    }
}
