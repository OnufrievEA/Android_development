public class MyTest {
    @Test(priority = 5)
    public static void test1(){
        System.out.println("test1");
    }

    @Test(priority = 10)
    public static void test2(){
        System.out.println("test2");
    }

    @Test(priority = 10)
    public static void test3(){
        System.out.println("test3");
    }

    @Test(priority = 6)
    public static void test4(){
        System.out.println("test4");
    }

    @Test(priority = 3)
    public static void test5(){
        System.out.println("test5");
    }

    @Test(priority = 8)
    public static void test6(){
        System.out.println("test6");
    }

    @Test()
    public static void test7(){
        System.out.println("test7");
    }

    @BeforeSuite
    public static void beforeTest(){
        System.out.println("In beforeTest method");
    }

//    @AfterSuite
//    public static void afterTest(){
//        System.out.println("In afterTest method");
//    }
}
