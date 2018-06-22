package com.rq.practice.adapter.base;

import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * ViewHolder的帮助类
 * 用于添加各种Listener事件
 * @author rock tou
 * @version 1.0.0
 */
public class ViewHolderHelper<T extends IAdapter> implements IAdapter.IViewHolder{

    // SparseArray用于存储View，作为一种缓存
    private SparseArray<View> mViews;

    private T mAdapter;

    private View mItemView;

    public ViewHolderHelper(){
        if (mViews == null){
            mViews = new SparseArray<>();
        }
    }

    public ViewHolderHelper(T adapter, View itemView){
        this();
        this.mItemView = itemView;
        this.mAdapter = adapter;
    }

    public void setAdapter(T adapter){
        this.mAdapter = adapter;
    }

    public void setItemView(View itemView){
        this.mItemView = itemView;
    }

    /**
     * 添加子View的click事件
     * @param id
     * @param position
     */
    @Override
    public void addOnClickListener(int id, int position){
        View view = getItemView(id);
        addOnClickListener(view, position);
    }

    /**
     * 添加子View的click事件
     * @param view
     * @param position
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addOnClickListener(final View view, final int position){
        if (view != null){
            if (!view.isClickable()){
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IAdapterChildClickListener.OnItemChildClickListener childClickListener =
                            mAdapter.getItemChildClickListener();
                    if (childClickListener != null){
                        childClickListener.onItemChildClick(mAdapter, view, position);
                    }
                }
            });
        }
    }

    /**
     * 添加子View的longClick事件
     * @param id
     * @param position
     */
    @Override
    public void addOnLongClickListener(int id, int position){
        View view = getItemView(id);
        addOnLongClickListener(view, position);
    }

    /**
     * 添加子View的longClick事件
     * @param view
     * @param position
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addOnLongClickListener(final View view, final int position){
        if (view != null){
            if (!view.isLongClickable()){
                view.setLongClickable(true);
            }
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    IAdapterChildClickListener.OnItemChildLongClickListener childLongClickListener =
                            mAdapter.getItemChildLongClickListener();
                    if (childLongClickListener != null){
                        return  childLongClickListener.onItemChildLongClick(mAdapter, view, position);
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 添加子View的touchClick事件
     * @param id
     * @param position
     */
    @Override
    public void addOnTouchClickListener(int id, int position){
        View view = getItemView(id);
        addOnTouchClickListener(view, position);
    }

    /**
     * 添加子View的touchClick事件
     * @param view
     * @param position
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addOnTouchClickListener(final View view, final int position){
        if (view != null){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    IAdapterChildClickListener.OnItemChildTouchClickListener childTouchClickListener =
                            mAdapter.getItemChildTouchClickListener();
                    if (childTouchClickListener != null){
                        return childTouchClickListener.onItemChildTouchClick(mAdapter, view, position);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public <T extends View> T getItemView(int id) {
        View view = mViews.get(id);
        if (view == null){
            view = mItemView.findViewById(id);
            mViews.put(id, view);
        }
        return (T)view;
    }
}
