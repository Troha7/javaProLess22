package ua.hillelit.lms.homework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This {@code @BeforeSuite} annotation indicates to the first executing method.
 * <p>
 *
 * @author Dmytro Trotsenko
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeSuite {

}
