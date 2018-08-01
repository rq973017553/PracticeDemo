package com.rq.practice.utils;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * &lt;一句话功能简述&gt;
 * &lt;功能详细描述&gt;
 *
 * @author ${user}
 * @version [版本号, ${date}]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RxJavaPractice {

    private RxJavaPractice(){}

    private static class Holder{
        private static final RxJavaPractice INSTANCE = new RxJavaPractice();
    }

    public static RxJavaPractice getInstance(){
        return Holder.INSTANCE;
    }

    public void ObservableAndObserverPractice(){

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("a");
                emitter.onNext("b");
                emitter.onNext("c");
                emitter.onNext("d");
                emitter.onComplete();
            }
        });

        observable.subscribe(new SimpleObserver<String>(){
            @Override
            public void onNext(String s) {
                super.onNext(s);
                EasyLog.v("接受信息::"+s);
            }
        });
    }

    class SimpleObserver<T> implements Observer<T>{
        @Override
        public void onSubscribe(Disposable d) {
            // Empty Method
        }

        @Override
        public void onNext(T t) {
            // Empty Method
        }

        @Override
        public void onError(Throwable e) {
            // Empty Method
        }

        @Override
        public void onComplete() {
            // Empty Method
        }
    }
}
