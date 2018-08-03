package com.rq.practice.framework.view;

/**
 * BaseView
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public interface BaseView {

    void showErrorMsg(String msg);

    //=======  State  =======
    //展示异常提示
    void showError();

    //数据为空时展示
    void showEmpty();

    //开始加载中
    void startLoading();

    // 停止加载
    void stopLoading();
}
