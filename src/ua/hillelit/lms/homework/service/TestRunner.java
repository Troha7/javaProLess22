package ua.hillelit.lms.homework.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ua.hillelit.lms.homework.annotations.AfterSuite;
import ua.hillelit.lms.homework.annotations.BeforeSuite;
import ua.hillelit.lms.homework.annotations.Test;

/**
 * {@link TestRunner} is a class for testing test-methods.
 * <p>Public method {@code start} takes an object of the class-type object.
 * <p>{@code  Class classTest} contains test-methods.
 * Test-methods in classTest must be public!
 * Test-methods ran only by priority depending on the annotations:
 * <p>{@link BeforeSuite} has the highest priority for running method, and can be set only once.
 * If not trows {@exception IllegalAccessException}.
 * <p>{@link Test} run methods for priority value (1...9).
 * If the priority value is not in these aisles, method will not be run.
 * <p>{@link AfterSuite} has the lowest priority for running method, and can be set only once.
 * If not trows {@exception IllegalAccessException}.
 *<p>
 *
 * @author Dmytro Trotsenko
 */

public class TestRunner {

  private final static int MIN = 0; //MIN priority level
  private final static int MAX = 10; //MAX priority level

  public static void start(Class<?> classTest)
      throws InstantiationException, IllegalAccessException, InvocationTargetException {

    Object aClassTest = classTest.newInstance();

    for (int priority = MIN; priority <= MAX; priority++) {
      int count = 0;
      for (Method method : aClassTest.getClass().getMethods()) {
        BeforeSuite beforeSuite = method.getAnnotation(BeforeSuite.class);
        Test test = method.getAnnotation(Test.class);
        AfterSuite afterSuite = method.getAnnotation(AfterSuite.class);

        if (isAnnotation(beforeSuite) && priority == MIN) {
          count++; //count annotations
          multipleAnnotationException(method, beforeSuite, count);
          method.invoke(aClassTest);

        } else if (isAnnotation(test) && priority == test.value()) {
          if (priority > MIN && priority < MAX) {
            method.invoke(aClassTest);
          }

        } else if (isAnnotation(afterSuite) && priority == MAX) {
          count++; //count annotations
          multipleAnnotationException(method, afterSuite, count);
          method.invoke(aClassTest);

        }
      }
    }
  }

  private static boolean isAnnotation(Annotation annotation) {
    return annotation != null;
  }

  //Throw IllegalAccessException when annotation exists on classTest more than once.
  private static void multipleAnnotationException(Method method, Annotation annotation, int count)
      throws IllegalAccessException {
    String warning =
        "[" + annotation + "] Not allowed to use more then once! [" + method.toString() + "]";
    if (count > 1) {
      throw new IllegalAccessException(warning);
    }
  }

}
