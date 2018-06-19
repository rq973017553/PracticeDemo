package com.rq.practice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.rq.practice.R;
import com.rq.practice.bean.PracticeBean;
import com.rq.practice.utils.EasyLog;

import java.util.List;

/**
 * Created by Rock You on 2017/12/16.
 */
public class MainAdapter extends BaseRecyclerAdapter<PracticeBean>{


    public MainAdapter(Context context, List<PracticeBean> listData) {
        super(context, listData);
        EasyLog.v("listData::"+listData.size());
    }

    public MainAdapter(Context context){
        super(context);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.main_item;
    }


    @Override
    protected void bindHolder(BaseViewHolder holder, int position) {
        Button button = holder.getItemView(R.id.main_item_btn);
        // 添加子View的点击事件
        holder.addOnClickListener(button, position);
        PracticeBean practiceBean = mListData.get(position);
        String text = practiceBean.getText();
        if (!TextUtils.isEmpty(text)){
            button.setText(text);
        }
    }
}
