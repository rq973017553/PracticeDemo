package com.rq.practice.adapter.base;

import android.view.View;

/**
 * Adapter和ViewHolder的接口集合
 * @author rock you
 * @version 1.0.0
 */
public interface IAdapter {

    /**
     * 设置Item的子View的click监听事件
     * @param itemChildClickListener
     */
    void setItemChildClickListener(IAdapterChildClickListener.OnItemChildClickListener itemChildClickListener);

    /**
     * 设置Item的子View的longClick监听事件
     * @param itemChildLongClickListener
     */
    void setItemChildLongClickListener(IAdapterChildClickListener.OnItemChildLongClickListener itemChildLongClickListener);

    /**
     * 设置Item的子View的touchClick监听事件
     * @param itemChildTouchClickListener
     */
    void setItemChildTouchClickListener(IAdapterChildClickListener.OnItemChildTouchClickListener itemChildTouchClickListener);

    /**
     * 获得Item的子View的click监听事件
     * @return
     */
    IAdapterChildClickListener.OnItemChildClickListener getItemChildClickListener();

    /**
     * 获得Item的子View的longClick监听事件
     * @return
     */
    IAdapterChildClickListener.OnItemChildLongClickListener getItemChildLongClickListener();

    /**
     * 获得Item的子View的touchClick监听事件
     * @return
     */
    IAdapterChildClickListener.OnItemChildTouchClickListener getItemChildTouchClickListener();

    interface IViewHolder{

            <T extends View> T getItemView(int id);

            void addOnClickListener(int id, int position);

            void addOnClickListener(final View view, final int position);

            void addOnLongClickListener(int id, int position);

            void addOnLongClickListener(final View view, final int position);

            void addOnTouchClickListener(int id, int position);

            void addOnTouchClickListener(final View view, final int position);
    }
}
