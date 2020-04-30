package saravan.didiy;

public class MyTest {

    public static void main (String[] args) {
        Di di = new Di();
        A a = (A) di.getBean(A.class);
        a.name = "Saravan";
        A another = (A) di.getBean(A.class);
        A A2 = A.getInstance();
        System.out.println("pause");

        // Please refer further on Test source code for JUnit tests on DIFactoryTest


    }
}
