package saravan.didiy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DIFactoryTest {

    Di di = null;

    @Before
    public void beforeEachTestMethod() {
        di = new Di();
        di.clearProto();
    }

    @Test
    public void createSingletonBean() {

        A first = (A) di.getBean(A.class);
        assertNotNull(first);
    }

    @Test
    public void createPrototypeBean() {
        B first = (B) di.getBean(new ObjectKey(B.class.getName(),"santhosh-proto"));
        assertNotNull(first);
    }

    @Test
    public void cBean()  {
        C first = (C) di.getBean("C");
        assertNotNull(first);
    }

    @Test
    public void singletonVariousTest() {

        A first = (A) di.getBean(A.class);
        first.name = "saravan-single";
        A second = (A) di.getBean(A.class);
        assertEquals (first.name, second.name);
        A third = A.getInstance();
        assertEquals (first.name, third.name);


    }

    @Test
    public void prototypeVariousTest() {

        B first = (B) di.getBean(new ObjectKey(B.class.getName(),"santhosh-proto"));
        B second = (B) di.getBean(new ObjectKey(B.class.getName(),"santhosh-proto"));
        assertEquals (first, second);
        assertEquals(di.numberOfProtoObjects(), 1);
        first.name = "Itsme";
        assertEquals (second.name, "Itsme");

        B third = (B) di.getBean(new ObjectKey(B.class.getName(),"santhosh-proto-third"));
        assertFalse  (first.name.equals(third.name));
        assertEquals(di.numberOfProtoObjects(), 2);


    }

    @Test
    public void getBeanCommonTest()  {

        A first = (A) di.getBean("A");
        first.name = "saravan-single";
        A second = (A) di.getBean("A");
        assertEquals (first.name, second.name);
        A third = A.getInstance();
        assertEquals (first.name, third.name);


        B fourth = (B) di.getBean("B","santhosh-proto");
        B fifth = (B) di.getBean("B","santhosh-proto");
        assertEquals (fourth, fifth);
        assertEquals(di.numberOfProtoObjects(), 1);
        fourth.name = "Itsme";
        assertEquals (fifth.name, "Itsme");

        B sixth = (B) di.getBean("B","santhosh-proto-third");
        assertFalse  (fourth.name.equals(sixth.name));
        assertEquals(di.numberOfProtoObjects(), 2);


    }

    @Test(expected = BeanNotFoundException.class)
    public void negativeUseCase1()  {
        C first = (C) di.getBean("D");
    }

    @Test(expected = BeanNotFoundException.class)
    public void negativeUseCase2()  {
        C first = (C) di.getBean("C", "myC");
        assertNull(first);
        // system is thinking it is prototype since you are passing second parameter
        // u may need to consider it is still in infancy stage
    }

}