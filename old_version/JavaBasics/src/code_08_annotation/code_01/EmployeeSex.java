package code_08_annotation.code_01;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface EmployeeSex {
    public enum Sex{男,女};
    Sex sex() default Sex.男;
}
