import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestImpl {
    public static void start(Class classInstance) {
        startTesting(classInstance);
    }

    public static void start(String className) throws ClassNotFoundException {
        startTesting(Class.forName(className));
    }

    private static void startTesting(Class classInstance) throws RuntimeException {
        Object testObj = null;
        Method beforeSuite = null;
        Method afterSuite = null;
        List<List<Method>> testList = new ArrayList<List<Method>>(10);

        try {
            testObj = classInstance.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            testList.add(new ArrayList<>());
        }

        Method[] methods = classInstance.getDeclaredMethods();
        for (Method o : methods) {

            if (o.getAnnotation(BeforeSuite.class) != null) {

                if (beforeSuite != null) {

                    throw new RuntimeException("BeforeSuite не в единичном экземпляре");
                }
                beforeSuite = o;
            }
            if (o.getAnnotation(AfterSuite.class) != null) {

                if (afterSuite != null) {

                    throw new RuntimeException("AfterSuite не в единичном экземпляре");
                }
                afterSuite = o;
            }
            if (o.getAnnotation(Test.class) != null) {

                int priority = o.getAnnotation(Test.class).priority();

                if ((priority > 0) && (priority <= 10)) {

                    testList.get(priority - 1).add(o);
                }
            }
        }

        try {
            beforeSuite.invoke(testObj);
            for (List<Method> methodsList : testList) {
                for (Method testMethod : methodsList) {
                    testMethod.invoke(testObj);
                }
            }
            afterSuite.invoke(testObj);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @Test
    public void testDefaultPrior() {
        System.out.println("Test default");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(priority = 7)
    public void test7() {
        System.out.println("Test 7");
    }

    @Test(priority = 9)
    public void test9() {
        System.out.println("Test 9");
    }

//    @BeforeSuite
//    public void beforeSuite2() {
//        System.out.println("Before Suite2");
//    }

    @Test(priority = 8)
    public void test8() {
        System.out.println("Test 8");
    }

    @Test(priority = 6)
    public void test6() {
        System.out.println("Test 6");
    }

    @Test(priority = 4)
    public void test4() {
        System.out.println("Test 4");
    }

    @Test(priority = 3)
    public void test3() {
        System.out.println("Test 3");
    }

    @Test(priority = 2)
    public void test2() {
        System.out.println("Test 2");
    }

    @Test(priority = 5)
    public void test5() {
        System.out.println("Test 5");
    }

    @Test(priority = 10)
    public void test10() {
        System.out.println("Test 10");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }
}
