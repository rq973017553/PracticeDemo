package com.rq.practice.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类工具
 * 对于'Proxy.newProxyInstance'的封装
 * @author rock you
 * @version 1.0.0
 */
public class ProxyTools {

    private ProxyInterceptor mInterceptor;

    private ProxyTools() {
    }

    private static final class Holder {
        private static final ProxyTools INSTANCE = new ProxyTools();
    }

    public static ProxyTools newInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取代理对象
     * @param object 被代理的对象
     * @param interceptor ProxyTools.ProxyInterceptor接口
     * @param <S> T类型的父接口
     * @param <T> 被代理的数据类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public <S, T extends S> S getProxy(T object, ProxyInterceptor interceptor) {
        if (object == null) {
            throw new NullPointerException("agent object is null!");
        }
        if (interceptor == null) {
            throw new NullPointerException("interceptor is null!");
        }
        this.mInterceptor = interceptor;
        Invocation<T> invocation = new Invocation<>(object);
        Object proxy = Proxy.newProxyInstance(ProxyTools.class.getClassLoader(),
                object.getClass().getInterfaces(), invocation);
        return (T) proxy;
    }

    /**
     * InvocationHandler的实例化
     * @param <T>
     */
    private class Invocation<T> implements InvocationHandler {

        private T t;

        Invocation(T t) {
            this.t = t;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            return mInterceptor.interceptor(t, method, args);
        }
    }

    /**
     * 对外暴露的代理接口
     */
    public interface ProxyInterceptor {
        Object interceptor(Object proxy, Method method, Object[] args) throws Throwable;
    }
}
