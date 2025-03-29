package com.sky.aspect;

import com.sky.annomation.AutoFill;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;

import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充
 */
@Aspect//切面注解
@Component//组件注解
@Slf4j//日志注解
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annomation.AutoFill)")//切入点
    public void autoFillPointCut() {//切入点

    }
    /**
     * 前置通知，在目标方法执行前执行
     */
    @Before("autoFillPointCut()")//切入点为autoFillPointCut()
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");
        /**
         * 获取目标方法上的注解
         * 通过反射获取注解
         * 反射是什么？
         * 反射就是通过类名获取类的信息，比如类的属性和方法，类的构造方法，类的父类，类的接口等。
         */
        /**
         * 获取签名
         */
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//获取签名
        AutoFill autoFill = methodSignature.getMethod().getAnnotation(AutoFill.class);//获取注解
        for (Object arg : joinPoint.getArgs()) {//获取参数
            if (arg != null) {//如果参数不为空
                try {
                    if (autoFill != null) {
                        Object currentUser = BaseContext.getCurrentId();
                        switch (autoFill.value()) {
                            case INSERT:
                                arg.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
                                arg.getClass().getDeclaredMethod("setCreateUser", Long.class).invoke(arg, currentUser);
                                arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
                                arg.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(arg, currentUser);
                                BaseContext.removeCurrentId();
                                break;
                            case UPDATE:
                                arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class).invoke(arg, LocalDateTime.now());
                                arg.getClass().getDeclaredMethod("setUpdateUser", Long.class).invoke(arg, currentUser);
                                BaseContext.removeCurrentId();
                                break;
                            default:
                                break;
                        }
                    }
                }//如果反射失败，则抛出异常
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
