package com.sky.context;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();// 保存当前登录用户的id

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }// 设置当前登录用户的id

    public static Long getCurrentId() {
        return threadLocal.get();
    }// 获取当前登录用户的id

    public static void removeCurrentId() {
        threadLocal.remove();
    }// 移除当前登录用户的id

}
