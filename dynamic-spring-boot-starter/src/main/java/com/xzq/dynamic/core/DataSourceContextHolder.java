package com.xzq.dynamic.core;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/11 17:09
 * @Version 1.0.0
 **/
public class DataSourceContextHolder {
    /**
     * 使用LIFO 队列，后进先出
     */
    private static final ThreadLocal<Deque<String>> DB_KEY_HOLDER = new ThreadLocal<Deque<String>>() {
        @Override
        protected Deque<String> initialValue() {
            return new ArrayDeque<>();
        }
    };
    /**
     * 获得当前线程数据源Key
     *
     * @return 数据源名称
     */
    public static String peek(){
       return DB_KEY_HOLDER.get().peek();
    }

    /**
     * 设置当前线程数据源Key
     *
     * @return 数据源名称
     */
    public static void push(String key){
         DB_KEY_HOLDER.get().push(key);
    }

    /**
     * 清除当前线层数据源
     */
    public static void poll() {
        Deque<String> deque = DB_KEY_HOLDER.get();
        deque.poll();
        if (deque.isEmpty()) {
            clean();
        }
    }
    public static void clean() {
        DB_KEY_HOLDER.remove();
    }
}
