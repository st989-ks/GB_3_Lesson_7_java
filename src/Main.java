public class Main {
    public static void main(String[] args) {
//        TestImpl.start(TestImpl.class);

        try {
            TestImpl.start("TestImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
