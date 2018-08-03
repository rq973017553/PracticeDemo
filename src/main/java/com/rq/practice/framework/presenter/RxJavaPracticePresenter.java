package com.rq.practice.framework.presenter;

import com.rq.practice.framework.contract.RxJavaPracticeContract;
import com.rq.practice.utils.RxJavaPractice;

/**
 * RxJavaPracticePresenter类
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public class RxJavaPracticePresenter extends CommonPresenter<RxJavaPracticeContract.RxJavaPracticeActivityView> implements RxJavaPracticeContract.RxJavaPracticePresenter {

    @Override
    public void startRxJavaPractice() {
        RxJavaPractice.getInstance().zipPractice(new RxJavaPractice.PracticeListener() {
            @Override
            public void showRxJavaData(String data) {
                mView.showRxJavaResult(data);
            }
        });
    }
}
