package com.rq.practice.utils;

/**
 *
 * 单例模式
 * 模仿android.util.Singleton
 *
 * @author rock you
 * @version [1.0.0 2018.6.6]
 */
public abstract class SingletonProvider<T> {

    private T instance = null;

    private SingletonProvider(){}

    public abstract T create();

    public final T getInstance(){
        if (instance == null){
            synchronized (SingletonProvider.class){
                if (instance == null){
                    instance = create();
                }
            }
        }
        return instance;
    }
}