package com.rq.practice.adapter;

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
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder>{

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    //数据源
    protected List<T> mListData;

    private BaseRecyclerAdapter.OnItemClickListener mItemClickListener;

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

    public void setItemClickListener(BaseRecyclerAdapter.OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    /**
     * 复写来自RecyclerView.Adapter的
     * onCreateViewHolder抽象方法
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getLayoutID(), parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }

    /**
     * 复写来自RecyclerView.Adapter的
     * onBindViewHolder抽象方法
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setItemClickListener(mItemClickListener, position);
        bindHolder(holder, position);
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
    protected abstract void bindHolder(BaseViewHolder holder, int position);

    /**
     * BaseViewHolder类
     * 静态类，防止持有外部类的引用，造成内存泄露
     * 在bindHolder中调用BaseViewHolder的
     * getItemView和getImageView，getTextView，getEditText
     */
    static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public void setItemClickListener(final BaseRecyclerAdapter.OnItemClickListener listener, final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(itemView, position);
                    }
                }
            });
        }

        /**
         * 自认为最重要的代码
         * 通过定义在方法上的泛型
         * 方法返回什么类型的View，就绑定什么类型的View
         * @param id
         * @param <T>
         * @return
         */
        public <T extends View> T getItemView(int id){
            T v = (T)itemView.findViewById(id);
            if (v == null){
                throw new IllegalArgumentException("itemView's id no exist or error!");
            }
            return v;
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
     * 整个ItemView的回调接口
     */
    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }
}