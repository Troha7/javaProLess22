package ua.hillelit.lms.homework.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import ua.hillelit.lms.homework.annotations.AfterSuite;
import ua.hillelit.lms.homework.annotations.BeforeSuite;
import ua.hillelit.lms.homework.annotations.Test;

/**
 * {@link TestRunner} is a class for testing test-methods.
 * <p>Public method {@code start} takes an object of the class-type object.
 * <p>{@code  Class classTest} contains test-methods.
 * Test-methods in classTest must be public! Test-methods ran only by priority depending on the
 * annotations:
 * <p>{@link BeforeSuite} has the highest priority for running method, and can be set only once.
 * If not trows {@exception IllegalAccessException}.
 * <p>{@link Test} run methods for priority value (1...10).
 * If the priority value is not in these aisles, method will not be run.
 * <p>{@link AfterSuite} has the lowest priority for running method, and can be set only once.
 * If not trows {@exception IllegalAccessException}.
 * <p>
 *
 * @author Dmytro Trotsenko
 */

public class TestRunner {

  private final static int MIN = 0; // MIN priority level
  private final static int MAX = 10; // MAX priority level

  public static void start(Class<?> classTest)
      throws InstantiationException, IllegalAccessException {

    Object testClass = classTest.newInstance();
    List<Method> beforeSuites = getAnnotationMethod(testClass, BeforeSuite.class);
    List<Method> test = getSortTestMethods(testClass, MIN, MAX);
    List<Method> afterSuites = getAnnotationMethod(testClass, AfterSuite.class);
    List<Method> collectedMethods = getCollectedMethods(beforeSuites, test, afterSuites);
    runAnnotationMethods(testClass, collectedMethods);

  }

  private static void runAnnotationMethods(Object testClass, List<Method> collectedMethods) {
    for (Method method : collectedMethods) {
      try {
        method.invoke(testClass);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static List<Method> getCollectedMethods(List<Method> beforeSuites, List<Method> test,
      List<Method> afterSuites) {
    List<Method> sortedMethods = new ArrayList<>();
    sortedMethods.addAll(beforeSuites);
    sortedMethods.addAll(test);
    sortedMethods.addAll(afterSuites);
    return sortedMethods;
  }

  private static List<Method> getSortTestMethods(Object testClass, int min, int max) {

    List<Method> methods = Arrays.asList(testClass.getClass().getMethods());

    return methods.stream()
        .filter(method -> method.isAnnotationPresent(Test.class))
        .filter(method -> method.getAnnotation(Test.class).value() > min
            && method.getAnnotation(Test.class).value() <= max)
        .sorted(Comparator.comparingInt(method -> method.getAnnotation(Test.class).value()))
        .collect(Collectors.toList());
  }

  private static List<Method> getAnnotationMethod(Object testClass,
      Class<? extends Annotation> annotation) {

    List<Method> methods = Arrays.asList(testClass.getClass().getMethods());

    List<Method> methodList = methods.stream()
        .filter(method -> method.isAnnotationPresent(annotation))
        .collect(Collectors.toList());

    multipleAnnotationException(methodList);

    return methodList;
  }

  //Throw IllegalAccessException when annotation exists on classTest more than once.
  private static void multipleAnnotationException(List<Method> methods) {
    String warning = "Not allowed to use more then once! " + methods;
    if (methods.size() > 1) {
      throw new IllegalStateException(warning);
    }
  }

}
