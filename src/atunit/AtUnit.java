package atunit;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import annotations.*;
import util.*;

/**
 * Created by cowerling on 16-3-10.
 */
public class AtUnit implements ProcessFiles.Strategy {
    static Class<?> testClass;
    static List<String> failedTests = new ArrayList<String>();
    static long testsRun = 0;
    static long failures = 0;

    public static void main(String[] args) throws Exception {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

        new ProcessFiles(new AtUnit(), "class").start(args);

        if(failures == 0)
            System.out.println("OK (" + testsRun + " tests)");
        else {
            System.out.println("(" + testsRun + " tests)");
            System.out.println("\n>>> " + failures + " FAILURE" + (failures > 1 ? "S" : "") + " <<<");
            for(String failedTest : failedTests)
                System.out.println(" " + failedTest);
        }
    }

    public void process(File classFile) {
        try {
            String className = ClassNameFinder.thisClass(BinaryFile.read(classFile));

            if(!className.contains("."))
                return;

            testClass = Class.forName(className);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        TestMethods testMethods = new TestMethods();
        Method creator = null, cleanup = null;

        for(Method method : testClass.getDeclaredMethods()) {
            testMethods.addIfTestMethod(method);

            if(creator == null)
                creator = checkForCreatorMethod(method);

            if(cleanup == null)
                cleanup = checkForCleanupMethod(method);
        }

        if(testMethods.size() > 0) {
            if(creator == null) {
                try {
                    if(!Modifier.isPublic(testClass.getDeclaredConstructor().getModifiers())) {
                        System.out.println("Error: " + testClass + " default constructor must be public");
                        System.exit(1);
                    }
                } catch (NoSuchMethodException e) {

                }
            }

            System.out.println(testClass.getName());
        }

        for(Method method : testMethods) {
            System.out.print("  . " + method.getName() + " ");

            try {
                Object testObject = createTestObject(creator);
                boolean success = false;

                try {
                    if(method.getReturnType().equals(boolean.class))
                        success = (Boolean)method.invoke(testObject);
                    else {
                        method.invoke(testObject);
                        success = true;
                    }
                } catch (InvocationTargetException e) {
                    System.out.println(e.getCause());
                }

                System.out.println(success ? "" : "(failed)");

                testsRun++;

                if(!success) {
                    failures++;
                    failedTests.add(testClass.getName() + ": " + method.getName());
                }

                if(cleanup != null)
                    cleanup.invoke(testObject, testObject);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class TestMethods extends ArrayList<Method> {
        void addIfTestMethod(Method method) {
            if(method.getAnnotation(Test.class) == null)
                return;

            if(!(method.getReturnType().equals(boolean.class) || method.getReturnType().equals(void.class)))
                throw new RuntimeException("@Test method must return boolean or void");

            method.setAccessible(true);
            add(method);
        }
    }

    private static Method checkForCreatorMethod(Method method) {
        if(method.getAnnotation(TestObjectCreate.class) == null)
            return null;

        if(!method.getReturnType().equals(testClass))
            throw new RuntimeException("@TestObjectCreate must return instance of Class to be tested");

        if((method.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCreate must be static.");

        method.setAccessible(true);
        return method;
    }

    private static Method checkForCleanupMethod(Method method) {
        if(method.getAnnotation(TestObjectCleanup.class) == null)
            return null;

        if(!method.getReturnType().equals(void.class))
            throw new RuntimeException("@TestObjectCleanup must return void");

        if((method.getModifiers() & Modifier.STATIC) < 1)
            throw new RuntimeException("@TestObjectCleanup must be static.");

        if(method.getParameterTypes().length == 0 || method.getParameterTypes()[0] != testClass)
            throw new RuntimeException("@TestObjectCleanup must take an argument of the tested type.");

        method.setAccessible(true);
        return method;
    }

    private static Object createTestObject(Method creator) {
        if(creator != null) {
            try {
                return creator.invoke(testClass);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't run @TestObject (creator) method");
            }
        } else {
            try {
                return testClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Couldn't create a test object. Try using a @TestObject method.");
            }
        }
    }
}
