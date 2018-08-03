package com.rq.practice.framework.contract;

import com.rq.practice.framework.presenter.BasePresenter;
import com.rq.practice.framework.view.BaseView;

/**
 * RxJavaPractice的Contract
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public interface RxJavaPracticeContract {

    interface RxJavaPracticeActivityView extends BaseView{

        // 显示RxJava结果
        void showRxJavaResult(String result);
    }

    interface RxJavaPracticePresenter extends BasePresenter<RxJavaPracticeActivityView>{

        // 启动RXJava练习
        void startRxJavaPractice();
    }
}
