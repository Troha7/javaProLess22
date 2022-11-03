package ua.hillelit.lms.homework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This {@code @Test} annotation indicates the execution priority of method.
 * <p>Valid values (1 ... 10). (Value = 1) is the highest priority.
 * <p>Default value (1).
 * <p>
 *
 * @author Dmytro Trotsenko
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
  int value() default 1;
}
