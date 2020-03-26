import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            start(MyTest.class);
        } catch (InvocationTargetException | IllegalAccessException e) {
        }
    }

    static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = testClass.getDeclaredMethods();
        List<Method> beforeMethods = new ArrayList();
        List<Method> afterMethods = new ArrayList();
        List<Method> testMethods = new ArrayList();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        testMethods.sort((o1, o2) -> {
            return o1.getAnnotation(Test.class).priority() - o2.getAnnotation(Test.class).priority();
        });

        Collections.reverse(testMethods);

        if (beforeMethods.size() > 1 || afterMethods.size() > 1) {
            throw new RuntimeException("Не может быть больше одного начального или завершающего метода");
        }

        if (beforeMethods.size() != 0) {
            beforeMethods.get(0).invoke(null);
        }

        if (testMethods.size() != 0) {
            for (Method testMethod : testMethods) {
                testMethod.invoke(null);
            }
        }

        if (afterMethods.size() != 0) {
            afterMethods.get(0).invoke(null);
        }
    }
}
