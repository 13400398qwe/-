package com.sky.annomation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解用于标识某个方法需要进行功能字段自动填充
 */
@Target(java.lang.annotation.ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)//运行时注解
/**
 *  什么是retention注解？
 *  retention注解是java提供的一种自定义注解，它有三个值，
 *  1.SOURCE：在源文件中有效，即源文件、class文件、class字节码文件，编译成class文件时，注解信息会丢失
 *  2.CLASS：在class文件中有效，即class文件、class字节码文件，编译成class文件时，注解信息会保留
 *  3.RUNTIME：在运行时有效，即class文件、class字节码文件、JVM加载class文件时，注解信息会保留
 *  默认是SOURCE，即在源文件中有效，编译成class文件时，注解信息会丢失
 *  @Retention(RetentionPolicy.RUNTIME)
 *  @Retention(RetentionPolicy.CLASS)
 *  @Retention(RetentionPolicy.SOURCE)
 *  他的作用就是用来标识某个注解的保留策略，即在什么阶段保留这个注解的信息。
 *   */
public @interface AutoFill {
    OperationType value();//操作类型
}
