package com.southeast.annotation3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.CLASS) // RetentionPolicy.CLASS : 在类加载的时候丢弃。
// 在字节码文件的处理中有用。注解默认使用这种方式
public @interface InjectPrint {
    String value();
}
