package com.rq.practice.activities.practice;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rq.practice.R;
import com.rq.practice.activities.base.BaseActivity;
import com.rq.practice.activities.base.MVPBaseActivity;
import com.rq.practice.framework.contract.RxJavaPracticeContract;
import com.rq.practice.framework.presenter.RxJavaPracticePresenter;
import com.rq.practice.utils.RxJavaPractice;

/**
 * &lt;一句话功能简述&gt;
 * &lt;功能详细描述&gt;
 *
 * @author ${user}
 * @version [版本号, ${date}]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RxJavaPracticeActivity extends MVPBaseActivity<RxJavaPracticeContract.RxJavaPracticePresenter> implements RxJavaPracticeContract.RxJavaPracticeActivityView{

    private Button mRunRxjavaBtn;

    private TextView mShowRxJavaRunResult;

    @Override
    public int getLayoutID() {
        return R.layout.activity_rxjava_pra;
    }

    @Override
    public void bindView() {
        mRunRxjavaBtn = findViewById(R.id.start_rx_java_btn);
        mRunRxjavaBtn.setOnClickListener(clickListener);
        mShowRxJavaRunResult = findViewById(R.id.show_rx_java_run_result);
    }

    @Override
    public void initData() {
        // 暂时这样写，后期使用Dagger2
        mPresenter = new RxJavaPracticePresenter();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start_rx_java_btn:
                    mPresenter.startRxJavaPractice();
                    break;
            }
        }
    };

    @Override
    public void showRxJavaResult(String result) {
        mShowRxJavaRunResult.setText(result);
    }
}
