package ua.hillelit.lms.homework;

import java.lang.reflect.InvocationTargetException;
import ua.hillelit.lms.homework.service.TestRunner;
import ua.hillelit.lms.homework.testClasses.ClassTest;

public class Main {

  public static void main(String[] args) {

    try {
      TestRunner.start(ClassTest.class);
      //TestRunner.start(ClassTest2.class);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }


  }

}
