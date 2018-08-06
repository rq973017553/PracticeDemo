package com.rq.practice.utils;


import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava练习
 *
 * @author rock you
 * @version [1.0.0 2018.8.3]
 */
public class RxJavaPractice {

    private static final int MAX_VALUE = 5;

    private RxJavaPractice(){}

    private static class Holder{
        private static final RxJavaPractice INSTANCE = new RxJavaPractice();
    }

    public static RxJavaPractice getInstance(){
        return Holder.INSTANCE;
    }


    /**
     * map操作符练习
     * @param listener 接口
     * @return Disposable
     */
    public Disposable mapPractice(final PracticeListener listener){
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i=0; i < MAX_VALUE; i++){
                    emitter.onNext(i);
                    Thread.sleep(2*1000);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .map(new Function<Integer, String>() {
              @Override
              public String apply(Integer integer) {
                  if (integer != null){
                      return String.valueOf(integer);
                  }
                  return null;
              }
          }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (listener != null){
                    listener.showRxJavaData(s);
                }
            }
        });
    }

    /**
     * flatMap操作符练习
     * @param listener 接口
     * @return Disposable
     */
    public Disposable flatMapPractice(final PracticeListener listener){
       return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i=0; i < MAX_VALUE; i++){
                    emitter.onNext(i);
                    Thread.sleep(2*1000);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .flatMap(new Function<Integer, ObservableSource<String>>() {
              @Override
              public ObservableSource<String> apply(final Integer integer) throws Exception {
                  if (integer != null){
                      return Observable.create(new ObservableOnSubscribe<String>() {
                          @Override
                          public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                              emitter.onNext(String.valueOf(integer));
                          }
                      });
                  }
                  return null;
              }
          }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (listener != null){
                    listener.showRxJavaData(s);
                }
            }
        });
    }

    /**
     * zip操作符
     * @param listener 接口
     * @return Disposable
     */
    public Disposable zipPractice(final PracticeListener listener){
        // 两个Observable都有结果的时候才会展示

        //比如一个界面需要展示用户的一些信息, 而这些信息分别要从两个服务器接口中获取。
        // 而只有当两个都获取到了之后才能进行展示, 这个时候就可以用Zip了
        // 注意到两个Observable的sleep时间，短的sleep时间需要，等长的sleep时间结束后，一起被压缩成新的Observable
        Observable<Integer> observable0 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i=0; i < MAX_VALUE; i++){
                    emitter.onNext(i);
                    Thread.sleep(5*1000);
                    EasyLog.v("observable0::"+i);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                for (int i=0; i < MAX_VALUE; i++){
                    emitter.onNext(String.valueOf(i));
                    Thread.sleep(1*1000);
                    EasyLog.v("observable1::"+i);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        // zip操作符类似"木桶效应"，只有最短的一根才有效。
        return Observable.zip(observable0, observable1, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String str) throws Exception {
                if (integer == null || TextUtils.isEmpty(str)){
                    return "error!";
                }
                return String.valueOf(integer)+"::"+str;
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (listener != null){
                    listener.showRxJavaData(s);
                }
            }
        });
    }

    /**
     * 带列表的zip操作符
     * @param listener 接口
     * @return Disposable
     */
    public Disposable zipListPractice(final PracticeListener listener){
        List<Observable<Integer>> list = new ArrayList<>();
        for (int i = 0; i <MAX_VALUE; i ++){
            Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception{
                    for (int i = 0; i <MAX_VALUE; i++){
                        emitter.onNext(i);
                        Thread.sleep(2*1000);
                    }
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io());
            list.add(observable);
        }

        return Observable.zip(list, new Function<Object[], String>() {
            @Override
            public String apply(Object[] integers){
                for (Object obj: integers){
                    if (obj != null && obj instanceof Integer){
                        Integer integer = (Integer)obj;
                        return String.valueOf(integer);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                if (listener != null){
                    listener.showRxJavaData(s);
                }
            }
        });
    }

    /**
     * Observable和Observer练习
     * @param listener 接口
     */
    public void observableAndObserverPractice(final PracticeListener listener){

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                sendMsg(emitter);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new SimpleObserver<String>(){
              @Override
              public void onNext(String s) {
                  super.onNext(s);
                  if (listener != null){
                      listener.showRxJavaData(s);
                  }
              }
          });
    }

    private void sendMsg(ObservableEmitter<String> emitter) throws InterruptedException{
        emitter.onNext("a");
        Thread.sleep(2*1000);
        emitter.onNext("b");
        Thread.sleep(2*1000);
        emitter.onNext("c");
        Thread.sleep(2*1000);
        emitter.onNext("d");
        emitter.onComplete();
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

    public interface PracticeListener{

        void showRxJavaData(String data);
    }
}
