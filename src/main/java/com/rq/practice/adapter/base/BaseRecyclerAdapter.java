package com.rq.practice.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * RecyclerView.Adapter的再封装
 * 只需要复写两个abstract方法即可
 * Created by Rock you
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.RecyclerViewHolder> implements IAdapter{

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    //数据源
    protected List<T> mListData;

    // ItemClickListener
    private BaseRecyclerAdapter.OnItemClickListener mItemClickListener;

    // ItemChildClickListener
    private IAdapterChildClickListener.OnItemChildClickListener mItemChildClickListener;

    // ItemChildLongClickListener
    private IAdapterChildClickListener.OnItemChildLongClickListener mItemChildLongClickListener;

    // ItemChildTouchClickListener
    private IAdapterChildClickListener.OnItemChildTouchClickListener mItemChildTouchClickListener;

    /**
     * 构造函数
     * @param context
     */
    public BaseRecyclerAdapter(Context context){
        this(context, Collections.<T>emptyList());
    }

    /**
     * 构造函数
     * @param context
     * @param listData
     */
    public BaseRecyclerAdapter(Context context, List<T> listData){
        this.mListData = listData;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据源
     * @param listData
     */
    public void setListData(List<T> listData){
        this.mListData = listData;
    }

    /**
     * 设置Item的click监听事件
     * @param itemClickListener
     */
    public void setItemClickListener(BaseRecyclerAdapter.OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 设置Item的子View的click监听事件
     * @param itemChildClickListener
     */
    @Override
    public void setItemChildClickListener(IAdapterChildClickListener.OnItemChildClickListener itemChildClickListener){
        this.mItemChildClickListener = itemChildClickListener;
    }

    /**
     * 设置Item的子View的longClick监听事件
     * @param itemChildLongClickListener
     */
    @Override
    public void setItemChildLongClickListener(IAdapterChildClickListener.OnItemChildLongClickListener itemChildLongClickListener){
        this.mItemChildLongClickListener = itemChildLongClickListener;
    }


    /**
     * 设置Item的子View的touchClick监听事件
     * @param itemChildTouchClickListener
     */
    @Override
    public void setItemChildTouchClickListener(IAdapterChildClickListener.OnItemChildTouchClickListener itemChildTouchClickListener){
        this.mItemChildTouchClickListener = itemChildTouchClickListener;
    }

    /**
     * 获得Item的子View的click监听事件
     * @return
     */
    @Override
    public IAdapterChildClickListener.OnItemChildClickListener getItemChildClickListener() {
        return mItemChildClickListener;
    }

    /**
     * 获得Item的子View的longClick监听事件
     * @return
     */
    @Override
    public IAdapterChildClickListener.OnItemChildLongClickListener getItemChildLongClickListener() {
        return mItemChildLongClickListener;
    }

    /**
     * 获得Item的子View的touchClick监听事件
     * @return
     */
    @Override
    public IAdapterChildClickListener.OnItemChildTouchClickListener getItemChildTouchClickListener() {
        return mItemChildTouchClickListener;
    }

    /**
     * 复写来自RecyclerView.Adapter的
     * onCreateViewHolder抽象方法
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getLayoutID(), parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(this, view);
        return viewHolder;
    }

    /**
     * 复写来自RecyclerView.Adapter的
     * onBindViewHolder抽象方法
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindHolder(holder, position);
        holder.setItemClickListener(mItemClickListener, position);
    }

    /**
     * 复写来自RecyclerView.Adapter的
     * getItemCount的抽象方法
     * @return
     */
    @Override
    public int getItemCount() {
        if (mListData == null){
            return 0;
        }
        return mListData.size();
    }

    /**
     * BaseRecyclerAdapter自带的方法
     * 用于提供每个Item的数据
     * @param position
     * @return
     */
    public T getItemData(int position){
        if (mListData == null){
            return null;
        }
        return mListData.get(position);
    }

    /**
     * 返回该itemView的id值
     * @return itemView的id
     */
    protected abstract int getLayoutID();

    /**
     * 绑定ViewHolder
     * 通过调用holder的getItemView得到任何View
     * 或者getImageView得到ImageView
     * getTextView得到TextView
     * getEditText得到EditText
     * 同时在做上述getxxx操作的过程中通过id find各个View
     * @param holder
     * @param position
     */
    protected abstract void bindHolder(RecyclerViewHolder holder, int position);

    /**
     * BaseViewHolder类
     * 静态类，防止持有外部类的引用，造成内存泄露
     * 在bindHolder中调用BaseViewHolder的
     * getItemView和getImageView，getTextView，getEditText
     */
    public static final class RecyclerViewHolder extends RecyclerView.ViewHolder implements IAdapter.IViewHolder{

        private BaseRecyclerAdapter mAdapter;

        private ViewHolderHelper<BaseRecyclerAdapter> mViewHolderHelper;

        RecyclerViewHolder(BaseRecyclerAdapter adapter, View itemView) {
            super(itemView);
            this.mAdapter = adapter;
            mViewHolderHelper = new ViewHolderHelper<>(adapter, itemView);
        }

        /**
         * RecyclerView的Item的事件监听
         * 如果以后该ViewHolder拿出来，就需要加上"public"
         * @param listener
         * @param position
         */
        void setItemClickListener(final BaseRecyclerAdapter.OnItemClickListener listener, final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(mAdapter, itemView, position);
                    }
                }
            });
        }

///////////////////////////////////////////////子View的点击事件////////////////////////////////////////////////////////

        /**
         * 添加子View的click事件
         * @param id
         * @param position
         */
        @Override
        public void addOnClickListener(int id, int position){
            mViewHolderHelper.addOnClickListener(id, position);
        }

        /**
         * 添加子View的click事件
         * @param view
         * @param position
         */
        @Override
        public void addOnClickListener(final View view, final int position){
            mViewHolderHelper.addOnClickListener(view, position);
        }

        /**
         * 添加子View的longClick事件
         * @param id
         * @param position
         */
        @Override
        public void addOnLongClickListener(int id, int position){
            mViewHolderHelper.addOnLongClickListener(id, position);
        }

        /**
         * 添加子View的longClick事件
         * @param view
         * @param position
         */
        @Override
        public void addOnLongClickListener(final View view, final int position){
            mViewHolderHelper.addOnLongClickListener(view, position);
        }

        /**
         * 添加子View的touchClick事件
         * @param id
         * @param position
         */
        @Override
        public void addOnTouchClickListener(int id, int position){
            mViewHolderHelper.addOnTouchClickListener(id, position);
        }

        /**
         * 添加子View的touchClick事件
         * @param view
         * @param position
         */
        @Override
        public void addOnTouchClickListener(final View view, final int position){
            mViewHolderHelper.addOnTouchClickListener(view, position);
        }

///////////////////////////////////////////////子View的点击事件////////////////////////////////////////////////////////

        /**
         * 自认为最重要的代码
         * 通过定义在方法上的泛型
         * 方法返回什么类型的View，就绑定什么类型的View
         * @param id
         * @param <T>
         * @return
         */
        @Override
        @SuppressWarnings("unchecked")
        public <T extends View> T getItemView(int id){
            return mViewHolderHelper.getItemView(id);
        }

        public ImageView getImageView(int id){
            return getItemView(id);
        }

        public TextView getTextView(int id){
            return getItemView(id);
        }

        public EditText getEditText(int id){
            return getItemView(id);
        }
    }

    /**
     * 整个ItemView的监听接口
     */
    public interface OnItemClickListener<T>{
        /**
         *
         * @param adapter
         * @param itemView
         * @param position
         */
        void onItemClick(T adapter, View itemView, int position);
    }
}
