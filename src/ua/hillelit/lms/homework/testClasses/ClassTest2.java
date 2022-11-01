package ua.hillelit.lms.homework.testClasses;

import ua.hillelit.lms.homework.service.TestRunner;
import ua.hillelit.lms.homework.annotations.AfterSuite;
import ua.hillelit.lms.homework.annotations.BeforeSuite;
import ua.hillelit.lms.homework.annotations.Test;

/**
 * {@link ClassTest2} is a class for testing.
 * <p>Test-methods in this class must be public & have annotation:
 * {@link BeforeSuite}, {@link Test}, {@link AfterSuite}.
 * <p>These annotations for run methods by priority value in class {@link TestRunner}.
 * <p>
 *
 * @author Dmytro Trotsenko
 */
public class ClassTest2 {

  @AfterSuite
  public void afterSuite() {
    System.out.println("AfterSuite!");
  }

  @Test(1)
  public void test1() {
    System.out.println("Test1");
  }

  @Test(1)
  public void test5() {
    System.out.println("Test5");
  }

  @Test(4)
  public void test4() {
    System.out.println("Test4");
  }

  @Test(4)
  public void test2() {
    System.out.println("Test2");
  }

  @Test(3)
  public void test3() {
    System.out.println("Test3");
  }

  @BeforeSuite
  public void beforeSuite() {
    System.out.println("BeforeSuite!");
  }

}
