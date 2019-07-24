package code_08_annotation.code_00;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 水果名称注解
 */
@Target(FIELD) //ElementType.FIELD:成员变量、对象、属性（包括enum实例）
@Retention(RUNTIME)// 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。
@Documented // Deprecated也是一种标记类型注解。
public @interface FruitName {
    public String fruitName() default "";
}
