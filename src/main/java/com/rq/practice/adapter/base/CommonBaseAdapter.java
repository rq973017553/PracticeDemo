package com.rq.practice.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * &lt;一句话功能简述&gt;
 * &lt;功能详细描述&gt;
 *
 * @author ${user}
 * @version [版本号, ${date}]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter implements IAdapter{

    private List<T> mListData;

    private Context mContext;

    protected LayoutInflater mLayoutInflater;

    public CommonBaseAdapter(Context context){
        this(context, Collections.<T>emptyList());
    }

    public CommonBaseAdapter(Context context, List<T> listData){
        this.mContext = context;
        this.mListData = listData;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void setItemChildClickListener(IAdapterChildClickListener.OnItemChildClickListener itemChildClickListener) {

    }

    @Override
    public void setItemChildLongClickListener(IAdapterChildClickListener.OnItemChildLongClickListener itemChildLongClickListener) {

    }

    @Override
    public void setItemChildTouchClickListener(IAdapterChildClickListener.OnItemChildTouchClickListener itemChildTouchClickListener) {

    }

    @Override
    public IAdapterChildClickListener.OnItemChildClickListener getItemChildClickListener() {
        return null;
    }

    @Override
    public IAdapterChildClickListener.OnItemChildLongClickListener getItemChildLongClickListener() {
        return null;
    }

    @Override
    public IAdapterChildClickListener.OnItemChildTouchClickListener getItemChildTouchClickListener() {
        return null;
    }

    @Override
    public int getCount() {
        if (mListData == null){
            return 0;
        }
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        if (mListData == null){
            return null;
        }
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder(this, convertView);
            convertView = mLayoutInflater.inflate(getLayoutID(), parent, false);
            createViewHolder(viewHolder);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindHolder(viewHolder, position);
        return convertView;
    }

    protected abstract int getLayoutID();


    protected abstract void createViewHolder(ViewHolder holder);


    protected abstract void bindHolder(ViewHolder holder, int position);


    public static final class ViewHolder implements IAdapter.IViewHolder{

        ViewHolderHelper<CommonBaseAdapter> mViewHolderHelper;

        ViewHolder(CommonBaseAdapter adapter, View itemView){
            mViewHolderHelper = new ViewHolderHelper<>(adapter, itemView);
        }

        @Override
        public <T extends View> T getItemView(int id) {
            return mViewHolderHelper.getItemView(id);
        }

        @Override
        public void addOnClickListener(int id, int position) {
            mViewHolderHelper.addOnClickListener(id, position);
        }

        @Override
        public void addOnClickListener(View view, int position) {
            mViewHolderHelper.addOnClickListener(view, position);
        }

        @Override
        public void addOnLongClickListener(int id, int position) {
            mViewHolderHelper.addOnLongClickListener(id, position);
        }

        @Override
        public void addOnLongClickListener(View view, int position) {
            mViewHolderHelper.addOnLongClickListener(view, position);
        }

        @Override
        public void addOnTouchClickListener(int id, int position) {
            mViewHolderHelper.addOnTouchClickListener(id, position);
        }

        @Override
        public void addOnTouchClickListener(View view, int position) {
            mViewHolderHelper.addOnTouchClickListener(view, position);
        }
    }
}
