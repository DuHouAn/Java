package com.southeast.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解类编写的一些规则：
 * （1） Annotation型定义为@interface,
 * 所有的Annotation会自动继承java.lang.Annotation这一接口,并且不能再去继承别的类或是接口。
 * （2）参数成员只能用public或默认(default)这两个访问权修饰
 * （3）参数成员只能用基本类型byte,short,char,int,long,float,double,boolean八种基本数据类型
 * 和String、Enum、Class、Annotations等数据类型,以及这一些类型的数组
 * （4）要获取类方法和字段的注解信息，必须通过Java的反射技术来获取 Annotation对象,因为除此之外没有别的获取注解对象的方法
 * （5）注解也可以没有定义成员, 不过这样注解就没啥用了
 * 注意：自定义注解需要使用到元注解
 */
/**
 * 水果颜色注解
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     */
    public enum Color{绿色,红色,青色};

    /**
     * 颜色属性 （注意：这里的属性指的就是方法）
     */
    Color fruitColor() default Color.绿色;//默认是是绿色的
}

