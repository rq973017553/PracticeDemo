package com.rq.practice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;

import com.rq.practice.R;
import com.rq.practice.utils.EasyLog;

import java.util.List;

/**
 * Created by Rock You on 2017/12/16.
 */
public class MainAdapter extends BaseRecyclerAdapter<String>{


    public MainAdapter(Context context, List<String> listData) {
        super(context, listData);
        EasyLog.v("listData::"+listData.size());
    }

    @Override
    protected int getLayoutID() {
        return R.layout.main_item;
    }


    @Override
    protected void bindHolder(BaseViewHolder holder, int position) {
        Button button = holder.getItemView(R.id.main_item_btn);
        String text = mListData.get(position);
        if (!TextUtils.isEmpty(text)){
            button.setText(text);
        }
    }
}
